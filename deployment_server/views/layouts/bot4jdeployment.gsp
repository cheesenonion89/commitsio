<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>
        <g:layoutTitle default="Bot4J Deployment"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <asset:stylesheet src="bootstrap.css"/>
    <asset:stylesheet src="bot4jdeployment.css"/>

    <asset:javascript src="bootstrap.js"/>

    <g:layoutHead/>
</head>
<body>

<div class="container">
    <div class="row">
        <div class="col-sm-12">
            <div class="page-header">
                <h1>Bot4JDeployment</h1>
            </div>
            <div class="navbar navbar-inverse navbar-static-top" role="navigation">
                <div class="container-fluid">
                    <ul class="nav navbar-nav">
                        <li>
                            <a class="" href="${createLink(uri: '/')}"><g:message code="default.home.label"
                                                                                      role="button"/></a>
                        </li>
                        <li>
                            <g:link class="create" action="create" role="button">
                                <g:message code="default.new.label" args="['Bot']"/>
                            </g:link>
                        </li>
                    </ul>
                </div>
            </div>
            <g:layoutBody/>
        </div>
    </div>
</div>



<div class="footer" role="contentinfo"></div>

<div id="spinner" class="spinner" style="display:none;">
    <g:message code="spinner.alt" default="Loading&hellip;"/>
</div>

<asset:javascript src="application.js"/>

</body>
</html>
