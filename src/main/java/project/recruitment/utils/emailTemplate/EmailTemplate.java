package project.recruitment.utils.emailTemplate;

public class EmailTemplate
{
    public static String getPasswordResetTemplate() {

        return "" +
                "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "  <meta charset=\"utf-8\">" +
                "  <meta http-equiv=\"x-ua-compatible\" content=\"ie=edge\">" +
                "  <title>Password Reset</title>" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">" +
                "  <style type=\"text/css\">" +
                "  @media screen {" +
                "    @font-face {" +
                "      font-family: 'Source Sans Pro';" +
                "      font-style: normal;" +
                "      font-weight: 400;" +
                "    " +
                "    }" +
                "    @font-face {" +
                "      font-family: 'Source Sans Pro';" +
                "      font-style: normal;" +
                "      font-weight: 700;" +
                "      " +
                "    }" +
                "  }" +
                "  body," +
                "  table," +
                "  td," +
                "  a {" +
                "    -ms-text-size-adjust: 100%%;" +
                "    -webkit-text-size-adjust: 100%%;" +
                "  }" +
                "  table," +
                "  td {" +
                "    mso-table-rspace: 0pt;" +
                "    mso-table-lspace: 0pt;" +
                "  }" +
                "  a[x-apple-data-detectors] {" +
                "    font-family: inherit !important;" +
                "    font-size: inherit !important;" +
                "    font-weight: inherit !important;" +
                "    line-height: inherit !important;" +
                "    color: inherit !important;" +
                "    text-decoration: none !important;" +
                "  }" +
                "" +
                "  div[style*=\"margin: 16px 0;\"] {" +
                "    margin: 0 !important;" +
                "  }" +
                "  body {" +
                "    width: 100%% !important;" +
                "    height: 100%% !important;" +
                "    padding: 0 !important;" +
                "    margin: 0 !important;" +
                "  }" +
                "  table {" +
                "    border-collapse: collapse !important;" +
                "  }" +
                "  a {" +
                "    color: #1a82e2;" +
                "  }" +
                "  </style>" +
                "</head>" +
                "<body style=\"background-color: #e9ecef;\">" +
                "  <!-- start body -->" +
                "  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%%\">" +
                "    <!-- start hero -->" +
                "    <tr>" +
                "      <td align=\"center\" bgcolor=\"#e9ecef\">" +
                "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%%\" style=\"max-width: 600px;\">" +
                "          <tr>" +
                "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 36px 24px 0; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; border-top: 3px solid #d4dadf;\">" +
                "              <h1 style=\"margin: 0; font-size: 32px; font-weight: 700; letter-spacing: -1px; line-height: 48px;\">Reset Your Password</h1>" +
                "            </td>" +
                "          </tr>" +
                "        </table>" +
                "      </td>" +
                "    </tr>" +
                "    <tr>" +
                "      <td align=\"center\" bgcolor=\"#e9ecef\">" +
                "       " +
                "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%%\" style=\"max-width: 600px;\">" +
                "          <tr>" +
                "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\">" +
                "              <p style=\"margin: 0;\">Tap the button below to reset your customer account password. If you didn't request a new password, you can safely delete this email.</p>" +
                "            </td>" +
                "          </tr>" +
                "          <tr>" +
                "            <td align=\"left\" bgcolor=\"#ffffff\">" +
                "              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%%\">" +
                "                <tr>" +
                "                  <td align=\"center\" bgcolor=\"#ffffff\" style=\"padding: 12px;\">" +
                "                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">" +
                "                      <tr>" +
                "                        <td align=\"center\" bgcolor=\"#1a82e2\" style=\"border-radius: 6px;\">" +
                "                          <a href=\"%s\" style=\"display: inline-block; padding: 16px 36px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; color: #ffffff; text-decoration: none; border-radius: 6px;\">reset password here</a>\n" +
                "                        </td>" +
                "                      </tr>" +
                "                    </table>" +
                "                  </td>" +
                "                </tr>" +
                "              </table>" +
                "            </td>" +
                "          </tr>" +
                "          <tr>" +
                "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\">" +
                "              <p style=\"margin: 0;\">If that doesn't work, copy and paste the following link in your browser:</p>" +
                "              <p style=\"margin: 0;\"><a href='%s'>%s</a></p>" +
                "            </td>" +
                "          </tr>" +
                "        </table>" +
                "      </td>" +
                "    </tr>" +
                "  </table>" +
                "</body>" +
                "</html>";
    }
}
