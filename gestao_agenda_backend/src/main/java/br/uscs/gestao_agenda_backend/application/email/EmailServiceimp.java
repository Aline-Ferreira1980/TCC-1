package br.uscs.gestao_agenda_backend.application.email;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;

import javax.mail.internet.MimeMessage;


@Service
@RequiredArgsConstructor
public class EmailServiceimp  implements EmailService{

    private final JavaMailSender mailSender; //envia o email

    @Value("${agenda.email.sender}")
    private String remetente;

    private final Configuration freemarkerConfig; //configura o html
    @Override
    public void enviar(Mensagem mensagem) {

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

            String corpo = processarTemplate(mensagem);

            helper.setFrom(remetente);
            helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
            helper.setSubject(mensagem.getAssunto());
            helper.setText(corpo, true);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailException("Não foi possível enviar e-mail", e);
        }

    }

    private String processarTemplate(Mensagem mensagem) {
        try {
            Template template = freemarkerConfig.getTemplate(mensagem.getCorpo());

            return FreeMarkerTemplateUtils.processTemplateIntoString(
                    template, mensagem.getVariaveis());
        } catch (Exception e) {
            throw new EmailException("Não foi possível montar o template do e-mail", e);
        }
    }
}
