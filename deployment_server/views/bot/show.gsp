<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="bot4jdeployment"/>
    <g:set var="entityName" value="${message(code: 'bot.label', default: 'Bot')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<div id="show-bot" class="content scaffold-show" role="main">
    <h3>${this.bot.name} Details</h3>
    <g:if test="${flash.message}">
        <div class="message alert alert-info" role="status">${flash.message}</div>
    </g:if>

    <div class="row">
        <div class="col-sm-3">
            <b>Bot ID:</b>
        </div>

        <div class="col-sm-9">
            ${this.bot.id}
        </div>
    </div>

    <div class="row"><br/></div>

    <div class="row">
        <div class="col-sm-3">
            <b>Name:</b>
        </div>

        <div class="col-sm-9">
            ${this.bot.name}
        </div>
    </div>

    <%--
    <div class="row"><br/></div>

    <div class="row">
        <div class="col-sm-3">
            <b>Deployment Destination:</b>
        </div>

        <div class="col-sm-9">
            ${this.bot.deploymentDestination}
        </div>
    </div>
    --%>
    <div class="row"><br/></div>

    <div class="row">
        <div class="col-sm-3">
            <b>Bot Type:</b>
        </div>

        <div class="col-sm-9">
            ${this.bot.botType}
        </div>
    </div>

    <div class="row"><br/></div>

    <div class="row">

        <div class="col-sm-3">
            <b>Facebook Configuration:</b>
        </div>

        <div class="col-sm-9">
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

    <div class="row"><br/></div>

    <div class="row">

        <div class="col-sm-3">
            <b>Slack Configuration:</b>
        </div>

        <div class="col-sm-9">
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

    <div class="row"><br/></div>


    <div class="row">

        <div class="col-sm-3">
            <b>Telegram Configuration:</b>
        </div>

        <div class="col-sm-9">
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

    <div class="row"><br/></div>


    <div class="row">

        <div class="col-sm-2">

            <g:form resource="${this.bot}">
                <fieldset class="buttons">
                    <g:link class="uploadTrainingData btn btn-info" action="uploadTrainingData"
                            resource="${this.bot}">Upload Training Data...</g:link>
                </fieldset>
            </g:form>
        </div>

        <div class="col-sm-2">

            <g:form resource="${this.bot}">
                <fieldset class="buttons">
                    <g:link class="startTransferLearning btn btn-info" action="startTransferLearning"
                            resource="${this.bot}">Start Transfer Learning</g:link>
                </fieldset>
            </g:form>
        </div>

        <div class="col-sm-2">

            <g:form resource="${this.bot}">
                <fieldset class="buttons">
                    <g:link class="deployBot btn btn-info" action="deployBot"
                            resource="${this.bot}">Deploy Bot</g:link>
                </fieldset>
            </g:form>
        </div>
    </div>

    <div class="row"><br/></div>

    <div class="row">

        <div class="col-sm-12">
            <g:form resource="${this.bot}" method="DELETE">
                <fieldset class="buttons">
                    <g:link class="edit btn btn-info" action="edit" resource="${this.bot}"><g:message
                            code="default.button.edit.label" default="Edit" role="button"/></g:link>
                    <input class="delete btn btn-danger" type="submit"
                           value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                           onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
                </fieldset>
            </g:form>
        </div>
    </div>

</div>
</body>
</html>
