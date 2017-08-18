<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="bot4jdeployment"/>
    <g:set var="entityName" value="${message(code: 'bot.label', default: 'Bot')}"/>
    <title><g:message code="default.edit.label" args="[entityName]"/></title>
</head>

<body>
<div id="edit-bot" class="content scaffold-edit" role="main">
    <h3><g:message code="default.edit.label" args="[entityName]"/></h3>
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
    <div class="row"><br/></div>

    <g:form resource="${this.bot}" method="PUT">
        <g:hiddenField name="version" value="${this.bot?.version}"/>

        <div class="form-group row">
            <label for="id" class="col-sm-3 col-form-label">Bot ID</label>

            <div class="col-sm-9">
                <input name="id" class="form-control" type="text" id="id" value="${this.bot.id}" readonly>
            </div>
        </div>

        <div class="form-group row">
            <label for="name" class="col-sm-3 col-form-label">Name</label>

            <div class="col-sm-9">
                <input name="name" class="form-control" type="text" id="name" value="${this.bot.name}" required>
            </div>
        </div>
        <%--
        <div class="form-group row">
            <label for="deploymentDestination" class="col-sm-3 col-form-label">Deployment Destination</label>

            <div class="col-sm-9">
                <input name="deploymentDestination" class="form-control" type="text" id="deploymentDestination"
                       value="${this.bot.deploymentDestination}" required>
            </div>
        </div>
        --%>
        <div class="form-group row">
            <label for="botType" class="col-sm-3 col-form-label">Bot Type</label>

            <div class="col-sm-9">
                <input name="botType" class="form-control" type="text" id="botType"
                       value="${this.bot.botType}" required>
            </div>
        </div>

        <div class="form-group row">
            <label for="facebookSpec" class="col-sm-3 col-form-label">Facebook Configuration</label>

            <div id="facebookSpec" class="col-sm-9">
                <g:if test="${this.bot.facebookSpec}">
                    <g:link class="showFacebookSpec btn btn-info" action="showFacebookSpec"
                            resource="${this.bot}">Show Facebook Configuration</g:link>
                </g:if>
                <g:else>
                    <g:link class="addFacebookSpec btn btn-info" action="addFacebookSpec"
                            resource="${this.bot}">Add Facebook Configuration</g:link>
                </g:else>
            </div>
        </div>

        <div class="form-group row">
            <label for="slackSpec" class="col-sm-3 col-form-label">Slack Configuration</label>

            <div id="slackSpec" class="col-sm-9">
                <g:if test="${this.bot.slackSpec}">
                    <g:link class="showSlackSpec btn btn-info" action="showSlackSpec"
                            resource="${this.bot}">Show Slack Configuration</g:link>
                </g:if>
                <g:else>
                    <g:link class="addSlackSpec btn btn-info" action="addSlackSpec"
                            resource="${this.bot}">Add Slack Configuration</g:link>
                </g:else>
            </div>
        </div>

        <div class="form-group row">
            <label for="telegramSpec" class="col-sm-3 col-form-label">Telegram Configuration</label>

            <div id="telegramSpec" class="col-sm-9">
                <g:if test="${this.bot.telegramSpec}">
                    <g:link class="showTelegramSpec btn btn-info" action="showTelegramSpec"
                            resource="${this.bot}">Show Telegram Configuration</g:link>
                </g:if>
                <g:else>
                    <g:link class="addTelegramSpec btn btn-info" action="addTelegramSpec"
                            resource="${this.bot}">Add Telegram Configuration</g:link>
                </g:else>
            </div>
        </div>


        <div class="row"><br/></div>

        <fieldset class="buttons">
            <input class="save btn btn-info" type="submit"
                   value="${message(code: 'default.button.update.label', default: 'Update')}"/>
        </fieldset>
    </g:form>
</div>
</body>
</html>
