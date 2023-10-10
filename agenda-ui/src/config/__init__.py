from typing import Optional

# from app.config.dev import DEVSettings
from src.config.main import GlobalConfig
# from app.config.prod import PRODSettings
# from app.config.qa import QASettings

DATETIME_FORMAT: str = "%Y-%m-%d %H:%M:%S"


# class FactoryConfig:
#     """Returns a config instance dependending on the ENV_STATE variable."""
#
#     def __init__(self, env_state: Optional[str]):
#         self.env_state = env_state
#
#     def __call__(self):
#         # if self.env_state == "dev":
#         #     return DEVSettings()
#         #
#         # elif self.env_state == "qa":
#         #     return QASettings()
#         #
#         # elif self.env_state == "prod":
#         #     return PRODSettings()
#
#
# ENV = FactoryConfig(GlobalConfig().ENV)()

ENV = GlobalConfig()
