<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="bot4jdeployment"/>
    <g:set var="entityName" value="${message(code: 'bot.label', default: 'Bot')}"/>
    <title><g:message code="default.edit.label" args="[entityName]"/></title>
</head>

<body>
<div id="edit-bot" class="content scaffold-edit" role="main">
    <h3>Upload Training Data</h3>
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

    <div class="row">
        <div class="col-sm-3">
            <b>Name:</b>
        </div>

        <div class="col-sm-9">
            ${this.bot.name}
        </div>
    </div>
    <%--
    <div class="row">
        <div class="col-sm-3">
            <b>Deployment Destination:</b>
        </div>

        <div class="col-sm-9">
            ${this.bot.deploymentDestination}
        </div>
    </div>
    --%>
    <div class="row">
        <div class="col-sm-3">
            <b>Bot Type:</b>
        </div>

        <div class="col-sm-9">
            ${this.bot.botType}
        </div>
    </div>

    <div class="row"><br/></div>

    <g:uploadForm name="sendTrainingData" action="sendTrainingData">
        <g:hiddenField name="id" value="${this.bot.id}"/>
        <g:hiddenField name="version" value="${this.bot.version}"/>

        <label class="btn btn-info" for="my-file-selector">
            <input id="my-file-selector" name="trainingDataFile" class="trainingDataFile" type="file"
                   style="display:none;"
                   onchange="
                       $('#upload-cnn_server-info').html($(this).val().replace('C:\\fakepath', '...'));
                       $('#uploadButton').attr('type', 'submit');">
            Browse
        </label>
        <span class='label label-info' id="upload-file-info"></span>

        <div class="row"><br/></div>

        <fieldset>
            <input id="uploadButton" class="sendTrainingData btn btn-info" type="hidden" value="Upload Training Data"/>
        </fieldset>

    </g:uploadForm>
</div>
</body>
</html>
