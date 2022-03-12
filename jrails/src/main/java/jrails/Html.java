package jrails;

public class Html {
    String content = "";
    public String toString() {
        return content;
    }

    public Html seq(Html h) {
        this.content = this.content + h.content;
        return this;
    }

    public Html br() {
        this.content = "<br/>";
        return this;
    }

    public Html t(Object o) {
        this.content = o.toString();
        return this;
    }

    public Html p(Html child) {
        this.content = "<p>"+child.content+"</p>";
        return this;
    }

    public Html div(Html child) {
        this.content = "<div>"+child.content+"</div>";
        return this;
    }

    public Html strong(Html child) {
        this.content = "<strong>"+child.content+"</strong>";
        return this;
    }

    public Html h1(Html child) {
        this.content = "<h1>"+child.content+"</h1>";
        return this;
    }

    public Html tr(Html child) {
        this.content = "<tr>"+child.content+"</tr>";
        return this;
    }

    public Html th(Html child) {
        this.content = "<th>"+child.content+"</th>";
        return this;
    }

    public Html td(Html child) {
        this.content = "<td>"+child.content+"</td>";
        return this;
    }

    public Html table(Html child) {
        this.content = "<table>"+child.content+"</table>";
        return this;
    }

    public Html thead(Html child) {
        this.content = "<thead>"+child.content+"</thead>";
        return this;
    }

    public Html tbody(Html child) {
        this.content = "<tbody>"+child.content+"</tbody>";
        return this;
    }

    public Html textarea(String name, Html child) {
        this.content = "<textarea name=\"" +  name + "\">"+child.content+"</textarea>";
        return this;
    }

    public Html link_to(String text, String url) {
        this.content = "<a href=\""+url + "\">" + text +"</a>";
        return this;
    }

    public Html form(String action, Html child) {
        this.content = "<form action=\""+action+"\" accept-charset=\"UTF-8\" method=\"post\">"+child.content+"</form>";
        return this;
    }

    public Html submit(String value) {
        this.content = "<input type=\"submit\" value=\""+value+"\"/>";
        return this;
    }
}