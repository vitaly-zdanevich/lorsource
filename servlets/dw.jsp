<%@ page contentType="text/html; charset=koi8-r"%>
<%@ page import="ru.org.linux.site.Template" errorPage="error.jsp"%>
<% Template tmpl = new Template(request, config, response); %>
<%= tmpl.head(); %>
<LINK REL=STYLESHEET TYPE="text/css" HREF="/<%= tmpl.getStyle() %>/dw-main.css">
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><marquee behavior="scroll" direction="up" height="400" width="155" ScrollAmount="1" ScrollDelay="100" onMouseOver="this.stop()" onMouseOut="this.start()">
          <script type="text/javascript" language="Javascript">

      var site_id = 40;
      var dw_rss_feed = 'http://www-128.ibm.com/developerworks/ru/views/rss'
      +'/customrssatom.jsp?feed_by=rss&zone_by=IBM+Systems'
      +',Java+technology,Web+services,Linux,XML,Open+sourc'
      +'e&type_by=Articles,Tutorials&search_by=&pubdate=01'
      +'/01/2007&max_entries=10&encoding=UTF-8';

      var num_of_articles = 10;
      var enc = 'UTF-8';

    </script>
          <script type="text/javascript"
      src="http://www-128.ibm.com/developerworks/everywhere/ew.js" language="Javascript">
    </script>
          </marquee>
        </td>
      </tr>
    </table>
</body>