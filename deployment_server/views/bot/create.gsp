<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="bot4jdeployment"/>
    <g:set var="entityName" value="${message(code: 'bot.label', default: 'Bot')}"/>
    <title><g:message code="default.create.label" args="[entityName]"/></title>
</head>

<body>

<div id="create-bot" class="content scaffold-create" role="main">
    <h3><g:message code="default.create.label" args="[entityName]"/></h3>
    <g:if test="${flash.message}">
        <div class="message alert alert-info" role="status">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${this.bot}">
        <ul class="errors list-group" role="alert">
            <g:eachError bean="${this.bot}" var="error">
                <li class="list-group-item list-group-item-warning"
                    <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                    <g:message error="${error}"/>
                </li>
            </g:eachError>
        </ul>
    </g:hasErrors>
    <g:form action="save" class="col-sm-12">
        <!--<f:all bean="bot"/>-->
        <div class="form-group row">
            <label for="name" class="col-sm-3 col-form-label">Name</label>

            <div class="col-sm-9">
                <input name="name" class="form-control" type="text" id="name" required>
            </div>
        </div>
        <%--
        <div class="form-group row">
            <label for="deploymentDestination" class="col-sm-3 col-form-label">Deployment Destination</label>

            <div class="col-sm-9">
                <input name="deploymentDestination" class="form-control" type="text" id="deploymentDestination" required>
            </div>
        </div>
        --%>

        <div class="form-group row">
            <label for="botType" class="col-sm-3 col-form-label">Bot Type</label>

            <div class="col-sm-9">
                <input name="botType" class="form-control" type="text" id="botType" required>
            </div>
        </div>

        <fieldset class="buttons">
            <g:submitButton name="create" class="save btn"
                            value="${message(code: 'default.button.create.label', default: 'Create')}"/>
        </fieldset>
    </g:form>
</div>
</body>
</html>
