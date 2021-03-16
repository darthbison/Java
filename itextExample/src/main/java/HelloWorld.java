
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;



public class HelloWorld {

    public void createPdf(String dest) throws Exception {
        PdfDocument pdf = new PdfDocument(new PdfWriter(dest));
        Document document = new Document(pdf);
        document.add(new Paragraph("Hello World!"));
        PdfFont font = PdfFontFactory
                .createFont(StandardFonts.TIMES_ROMAN);
        Div div = new Div().setFont(font).setFontSize(14);
        div.add(new Paragraph("iText is:"));
        List list = new List()
                .setSymbolIndent(12)
                .setListSymbol("\u2022");
        list.add(new ListItem("Never gonna give you up"))
                .add(new ListItem("Never gonna let you down"))
                .add(new ListItem("Never gonna run around and desert you"))
                .add(new ListItem("Never gonna make you cry"))

                .add(new ListItem("Never gonna say goodbye"))
                .add(new ListItem("Never gonna tell a lie and hurt you"));
        div.add(list);
        document.add(div);

        Image img = new Image(ImageDataFactory.create("/Users/johnson_marcus/image.jpg"))
                .setFixedPosition(300, 625);
        document.add(img);
        document.close();
    }

    public static void main(String[] args) {
        HelloWorld hw = new HelloWorld();
        try {

            hw.createPdf("/Users/johnson_marcus/test.pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
