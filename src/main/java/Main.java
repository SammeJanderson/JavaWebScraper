import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws IOException {

        //Esse regex vai dar match em quase todos os formatos de email
        Pattern emailRegex = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])");

        //criar um documento que contem o html, css e javascript do url
        Document document = Jsoup.connect("https://www.portalcasasderepouso.com.br/").get();

        //filtrar o elementos especificos nesse caso os links
        Elements links = document.select("a[href]");

        for (Element e : links) {
            //analisa o caminho absoluto da url e vê se ela contem o caminho detalhes
            if (e.attr("abs:href").contains("detalhes")) {

                //se conecta a nova pagina e extrai as infomações desejadas
                Document subpage = Jsoup.connect(e.attr("abs:href")).get();
                String name = subpage.getElementsByTag("h3").text().trim();
                String site = subpage.getElementsByClass("box site").text().toLowerCase().replace("site", "");
                String email = "";

                System.out.printf("\nNome: %s\nSite: %s\nEmail: %s\n--------------------------", name, site, email);
            }
        }
    }
}

