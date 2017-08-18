<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="bot4jdeployment"/>
    <g:set var="entityName" value="${message(code: 'facebookSpec.label', default: 'FacebookSpec')}"/>
    <title><g:message code="default.edit.label" args="[entityName]"/></title>
</head>

<body>
<div id="edit-facebookSpec" class="content scaffold-edit" role="main">
    <h1>Edit Facebook Configuration for
        <g:link controller="bot" action="show" id="${this.facebookSpec.bot.id}">
            ${this.facebookSpec.bot.name}
        </g:link>
    </h1>

    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>

    <g:hasErrors bean="${this.facebookSpec}">
        <ul class="errors list-group" role="alert">
            <g:eachError bean="${this.facebookSpec}" var="error">
                <li class="list-group-item list-group-item-warning"
                    <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                    <g:message error="${error}"/>
                </li>
            </g:eachError>
        </ul>
    </g:hasErrors>

    <div class="row"><br/></div>

    <g:form resource="${this.facebookSpec}" method="PUT">
        <g:hiddenField name="version" value="${this.facebookSpec?.version}"/>
        <div class="form-group row">
            <label for="platformName" class="col-sm-3 col-form-label">Platform</label>

            <div class="col-sm-9">
                <input name="platformName" class="form-control" type="text" id="platformName"
                       value="${this.facebookSpec.platformName}" readonly>
            </div>
        </div>

        <div class="form-group row">
            <label for="bot.id" class="col-sm-3 col-form-label">Bot</label>

            <div class="col-sm-9">
                <input name="bot.id" class="form-control" type="text" id="bot.id" value="${this.facebookSpec.bot.id}"
                       readonly>
            </div>
        </div>

        <div class="form-group row">
            <label for="accessToken" class="col-sm-3 col-form-label">Access Token</label>

            <div class="col-sm-9">
                <input name="accessToken" class="form-control" type="text" id="accessToken"
                       value="${this.facebookSpec.accessToken}">
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
