

 
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
 
public class EmailSender extends Thread {
  private String user = "carpooling14",
		  				password="alaska14", 
		  				host="smtp.gmail.com", 
		  				mittente="CarPooling_Unisa", 
		  				destinatario="", oggetto="",corpo="";
 
  public EmailSender(String destinatario, String oggetto, String corpo){
 
    this.destinatario = destinatario;
    this.oggetto = oggetto;
    this.corpo = corpo;
  }

  public void run() {
    int port = 465;
 
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.user", mittente);
    props.put("mail.smtp.host", host);
    props.put("mail.smtp.port", port);
  
    props.put("mail.smtp.starttls.enable","true");
    props.put("mail.smtp.socketFactory.port", port);
 
    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    props.put("mail.smtp.socketFactory.fallback", "false");
 
    Session session = Session.getInstance(props, null);
    session.setDebug(true);
 
    MimeBodyPart messageBodyPart1 = new MimeBodyPart();
 
    try{
      Multipart multipart = new MimeMultipart();
      MimeMessage msg = new MimeMessage(session);
 
      // header
      msg.setSubject(oggetto);
      msg.setSentDate(new Date());
      msg.setFrom(new InternetAddress(mittente));
 
      // destinatario
      msg.addRecipient(Message.RecipientType.TO,
      new InternetAddress(destinatario));
 
      // corpo
      messageBodyPart1.setText(corpo);
      multipart.addBodyPart(messageBodyPart1);

      msg.setContent(multipart);
 
      Transport transport = session.getTransport("smtps");
      transport.connect(host, user, password);
      transport.sendMessage(msg, msg.getAllRecipients());
      transport.close();
 
      System.out.println("Invio dell'email Terminato");
 
    }catch(AddressException ae) {
      ae.printStackTrace();
    }catch(NoSuchProviderException nspe){
      nspe.printStackTrace();
    }catch(MessagingException me){
      me.printStackTrace();
    }
  }
}