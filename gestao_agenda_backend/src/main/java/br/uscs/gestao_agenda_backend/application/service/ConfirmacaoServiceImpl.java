package br.uscs.gestao_agenda_backend.application.service;

import br.uscs.gestao_agenda_backend.application.email.EmailException;
import br.uscs.gestao_agenda_backend.application.email.EmailService;
import br.uscs.gestao_agenda_backend.application.email.Mensagem;
import br.uscs.gestao_agenda_backend.application.port.ConfirmacaoService;
import br.uscs.gestao_agenda_backend.domain.model.User;
import br.uscs.gestao_agenda_backend.domain.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
@RequiredArgsConstructor
public class ConfirmacaoServiceImpl implements ConfirmacaoService {

    @Value("${server.apiUrl}")
    private String apiUrl;

    @Value("${agenda.email.sender}")
    private String mailSender;

    private final JavaMailSender javaMailSender;

    private final UserRepository userRepository;

    private final EmailService emailService;

//    @Override
//    public void enviarEmailConfirmacao(String email, String token) {
//
//        // TODO: Melhorar email? Talvez usar um template?
//        String corpoEmail = "Clique neste link para confirmar seu e-mail: " +
//                apiUrl + "/confirm?token=" + token;
//
//
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom(mailSender);
//        message.setTo(email);
//        message.setSubject("Cadastro CESEP - Confirmação de E-mail");
//        message.setText(corpoEmail);
//
//        javaMailSender.send(message);
//    }

    @Override
    public void enviarEmailConfirmacao(String email, String token) {

        Mensagem mensagem = Mensagem.builder()
                .assunto("Cadastro CESEP - Confirmação de E-mail")
                .corpo("email_confirmacao.html")
                .variavel("link_confirmacao",  apiUrl + "/confirm?token=" + token)
                .destinatario(email)
                .build();

        emailService.enviar(mensagem);
//
//        // TODO: Melhorar email? Talvez usar um template?
//        String corpoEmail = "Clique neste link para confirmar seu e-mail: " +
//                apiUrl + "/confirm?token=" + token;
//
//
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom(mailSender);
//        message.setTo(email);
//        message.setSubject("Cadastro CESEP - Confirmação de E-mail");
//        message.setText(corpoEmail);
//
//        javaMailSender.send(message);
    }

    @Override
    public void confirmarEmail(String token) throws UsernameNotFoundException{
        User user = userRepository.findByToken(token).orElseThrow(
                () -> new UnauthorizedUserException("Token informado invalido")
        );
        user.setConfirmed(true);
        user.setToken(null);
        userRepository.save(user);
    }

}

