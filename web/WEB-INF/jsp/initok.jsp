<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 16.07.2015
  Time: 14:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<html>
  <head>
    <title></title>
    <%-- JQuery --%>
    <%@include file="/WEB-INF/include/jquery_lib_include.jsp" %>

    <%-- JQueryUI --%>
    <%@include file="/WEB-INF/include/jquery_ui_include.jsp" %>


    <script>
      $(function() {
        $( "#accordion" ).accordion();
      });
    </script>
  </head>
  <body>
  <div id="accordion">
    <h3>Section 1</h3>
    <div>
      <p>
        <img src="<c:url value="/images/ui-icons_222222_256x240.png"/>">
        
      </p>
    </div>
    <h3>Section 2</h3>
    <div>
      <p>
        Sed non urna. Donec et ante. Phasellus eu ligula. Vestibulum sit amet
        purus. Vivamus hendrerit, dolor at aliquet laoreet, mauris turpis porttitor
        velit, faucibus interdum tellus libero ac justo. Vivamus non quam. In
        suscipit faucibus urna.
      </p>
    </div>
    <h3>Section 3</h3>
    <div>
      <p>
        Nam enim risus, molestie et, porta ac, aliquam ac, risus. Quisque lobortis.
        Phasellus pellentesque purus in massa. Aenean in pede. Phasellus ac libero
        ac tellus pellentesque semper. Sed ac felis. Sed commodo, magna quis
        lacinia ornare, quam ante aliquam nisi, eu iaculis leo purus venenatis dui.
      </p>
      <ul>
        <li>List item one</li>
        <li>List item two</li>
        <li>List item three</li>
      </ul>
    </div>
    <h3>Section 4</h3>
    <div>
      <p>
        Cras dictum. Pellentesque habitant morbi tristique senectus et netus
        et malesuada fames ac turpis egestas. Vestibulum ante ipsum primis in
        faucibus orci luctus et ultrices posuere cubilia Curae; Aenean lacinia
        mauris vel est.
      </p>
      <p>
        Suspendisse eu nisl. Nullam ut libero. Integer dignissim consequat lectus.
        Class aptent taciti sociosqu ad litora torquent per conubia nostra, per
        inceptos himenaeos.
      </p>
    </div>
  </div>
  </body>
</html>
