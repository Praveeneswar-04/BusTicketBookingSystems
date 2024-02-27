package NotifcationDetails;

import java.time.LocalDateTime;


public class Email extends Notification{
   

    public Email() {
		super();
	}

	public static void sendTicketConfirmation() {
		new Email();
        // Code to send ticket confirmation email to recipientEmail
        try {
    		System.out.println("Time "+LocalDateTime.now());

			System.out.println("Ticket confirmation Email sent ");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
