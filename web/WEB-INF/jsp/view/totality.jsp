<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 17.09.2015
  Time: 16:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<div class="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title"><c:message code="label.statistic.totality.title"/></h3>
  </div>
  <div class="panel-body">
    <div class="row">
      <div class="col-lg-3 col-md-6">
        <div class="panel panel-green">
          <div class="panel-heading">
            <div class="row">
              <div class="col-xs-3">
                <i class="fa fa-comments fa-5x"></i>
              </div>
              <div class="col-xs-9 text-right">
                <div class="huge">${total_person_count}</div>
                <div><c:message code="label.statistic.totality.persons"/></div>
              </div>
            </div>
          </div>
          <div class="panel-footer">
            <span class="pull-left"><c:message code="label.statistic.totality.persons.title"/></span>
            <div class="clearfix"></div>
          </div>
        </div>
      </div>
      <div class="col-lg-3 col-md-6">
        <div class="panel panel-primary">
          <div class="panel-heading">
            <div class="row">
              <div class="col-xs-3">
                <i class="fa fa-tasks fa-5x"></i>
              </div>
              <div class="col-xs-9 text-right">
                <div class="huge">${dosser_count}</div>
                <div><c:message code="label.statistic.totality.dossers"/></div>
              </div>
            </div>
          </div>
          <div class="panel-footer">
            <span class="pull-left"><c:message code="label.statistic.totality.dossers.title"/></span>
            <div class="clearfix"></div>
          </div>
        </div>
      </div>
      <div class="col-lg-3 col-md-6">
        <div class="panel panel-yellow">
          <div class="panel-heading">
            <div class="row">
              <div class="col-xs-3">
                <i class="fa fa-shopping-cart fa-5x"></i>
              </div>
              <div class="col-xs-9 text-right">
                <div class="huge">${total_client_count}</div>
                <div><c:message code="label.statistic.totality.client"/></div>
              </div>
            </div>
          </div>
          <div class="panel-footer">
            <span class="pull-left"><c:message code="label.statistic.totality.client.title"/></span>
            <div class="clearfix"></div>
          </div>
        </div>
      </div>
      <div class="col-lg-3 col-md-6">
        <div class="panel panel-red">
          <div class="panel-heading">
            <div class="row">
              <div class="col-xs-3">
                <i class="fa fa-support fa-5x"></i>
              </div>
              <div class="col-xs-9 text-right">
                <div class="huge">${dosser_complaint_count}</div>
                <div><c:message code="label.statistic.totality.complaints"/></div>
              </div>
            </div>
          </div>
          <div class="panel-footer">
            <span class="pull-left"><c:message code="label.statistic.totality.complaints.title"/></span>
            <div class="clearfix"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
