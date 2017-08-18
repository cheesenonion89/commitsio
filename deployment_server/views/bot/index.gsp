<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="bot4jdeployment"/>
    <g:set var="entityName" value="${message(code: 'bot.label', default: 'Bot')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>


<div id="list-bot" class="content scaffold-list" role="main">
    <h3><g:message code="default.list.label" args="[entityName]"/></h3>
    <g:if test="${flash.message}">
        <div class="message alert alert-info" role="status">${flash.message}</div>
    </g:if>

    <table class="table table-striped">
        <thead class="">
        <tr>
            <th>Name</th>
            <%--<th>Deployment Destination</th>--%>
            <th>Bot Type</th>
            <th>Facebook Specs</th>
            <th>Slack Specs</th>
            <th>Telegram Specs</th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${botList}">
            <tr>
                <td>
            <g:link class="show" action="show" resource="${it}">
                ${it.name}
            </g:link>
            </td>
            <%--<td>${it.deploymentDestination}</td>--%>
            <td>${it.botType}</td>
            <g:if test="${it.facebookSpec}">
                <td><span class="glyphicon glyphicon-ok" style="color:green;" aria-hidden="true"></span></td>
            </g:if>
            <g:else>
                <td><span class="glyphicon glyphicon-remove" style="color:red;" aria-hidden="true"></span></td>
            </g:else>
            <g:if test="${it.slackSpec}">
                <td><span class="glyphicon glyphicon-ok" style="color:green;" aria-hidden="true"></span></td>
            </g:if>
            <g:else>
                <td><span class="glyphicon glyphicon-remove" style="color:red;" aria-hidden="true"></span></td>
            </g:else>
            <g:if test="${it.telegramSpec}">
                <td><span class="glyphicon glyphicon-ok" style="color:green;" aria-hidden="true"></span></td>
            </g:if>
            <g:else>
                <td><span class="glyphicon glyphicon-remove" style="color:red;" aria-hidden="true"></span></td>
            </g:else>
        </g:each>
        </tbody>
    </table>


    <div class="pagination">
        <g:paginate total="${botCount ?: 0}"/>
    </div>
</div>
</body>
</html>