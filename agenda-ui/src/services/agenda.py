import json
import urllib3
import requests

from urllib3.util.retry import Retry
from urllib.parse import urljoin, quote
from requests.auth import HTTPBasicAuth
from requests.adapters import HTTPAdapter
import base64

from src.config import ENV

HEADERS = {
    'Content-type': 'application/json',
    'Accept': 'application/json'
}

urllib3.disable_warnings()


class AgendaClient:
    def __init__(
        self,
        headers: dict = None,
        retries: int = 3,
        verify: bool = False,
        connection_timeout: int = 10,
        read_timeout: int = 30,
        proxies: dict = None
    ):
        _headers = {**HEADERS, **(headers or {})}
        self.session = self._build_session(ENV.AGENDA_CLIENT.BASE_URL, _headers, retries)
        self.verify_ssl = verify
        self.base_url = ENV.AGENDA_CLIENT.BASE_URL
        self.auth = None
        self.timeout_sec = (connection_timeout, read_timeout)
        self.proxies = proxies

    def _build_session(self, base_url, headers, retries):
        session = requests.Session()
        session.headers.update(headers)
        retry = self._build_retry_strategy(retries)
        session.mount(base_url, HTTPAdapter(max_retries=retry))
        return session

    def _build_retry_strategy(self, retries):
        return Retry(
            allowed_methods=['GET', 'PUT', 'POST', 'DELETE'],
            backoff_factor=1,
            total=retries,
        )

    def _request(self, method, path, **kwargs):
        request = self._build_request(method, path, **kwargs)
        try:
            response = self.session.send(
                request,
                verify=self.verify_ssl,
                timeout=self.timeout_sec,
                proxies=kwargs.get('proxies', self.proxies)
            )
            response.raise_for_status()
            message = response.reason
        except requests.HTTPError as err:
            text = err.response.text.replace('\n', '')
            err_message = f'{err.response.reason} {text}'
            message, response = err_message, err.response
        except requests.exceptions.SSLError as err:
            message, response = 'SSL error', err.response
        except requests.Timeout as err:
            message, response = 'Timeout error', err.response
        except requests.ConnectionError as err:
            message, response = 'Connection error', err.response
        except requests.RequestException as err:
            message, response = 'Request error', err.response

        evaluator = kwargs.get('evaluator', lambda r: r.json)
        return HTTPResponse(request, response, message, evaluator)

    def _build_request(self, method, path, **kwargs):
        data_type = kwargs.get('data_type', 'json')
        data = kwargs.get('data', None)
        if data_type == 'json' and data:
            data = json.dumps(data)

        request = requests.Request(
            method=method,
            url=urljoin(self.base_url, quote(str(path), safe="/.?+&=")),
            auth=self.auth,
            headers=kwargs.get('headers', {}),
            data=data,
            params=kwargs.get('params', None),
            cookies=kwargs.get('cookies', None)
        )
        return self.session.prepare_request(request)

    def get(self, path, **kwargs):
        return self._request('GET', path, **kwargs)

    def post(self, path, **kwargs):
        return self._request('POST', path, **kwargs)

    def put(self, path, **kwargs):
        return self._request('PUT', path, **kwargs)

    def delete(self, path, **kwargs):
        return self._request('DELETE', path, **kwargs)

    # def authenticate(self, login, password):
    #     self.auth = HTTPBasicAuth(login, password)

    def update_headers(self, name, value):
        self.session.headers.update({name: value})


class HTTPResponse:
    def __init__(self, request, response, message, evaluator):
        self.evaluator = evaluator
        self.response = response
        self.request = request
        self.message = message
        self.headers = request.headers
        self.method = request.method
        self.url = request.url

    @property
    def valid(self):
        return self.response is not None and self.response.ok

    @property
    def status(self):
        return None if self.response is None else self.response.status_code

    @property
    def bytes(self):
        return self.response.content if self.valid else b''

    @property
    def text(self):
        return self.response.text if self.response is not None else ''

    @property
    def json(self):
        try:
            return self.response.json()
        except (AttributeError, json.decoder.JSONDecodeError):
            return {}

    @property
    def value(self):
        if not self.valid:
            return ''
        try:
            return self.evaluator(self)
        except Exception as e:
            return f'HTTP evaluator error: {str(e)}'

    def __repr__(self):
        return f'method="{self.method}", url="{self.url}", ' \
               f'status="{self.status}", message="{self.message}"'