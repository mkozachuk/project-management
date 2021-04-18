<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<!--[if IE]><meta http-equiv="X-UA-Compatible" content="IE=edge"><![endif]-->
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="generator" content="Asciidoctor 1.5.3">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css">
</head>
<body class="book toc2 toc-left">
<div id="header">
<div id="toc" class="toc2">
<div id="toctitle">Table of Contents</div>
<ul class="sectlevel1">
<li><a href="#Overview">Overview</a></li>
<li><a href="#project-endpoint">Project endpoint</a>
<ul class="sectlevel3">
<li><a href="#create-new-project">Create new project</a></li>
<li><a href="#assign-project-to-client">Assign project to client</a></li>
<li><a href="#assign-project-to-employee">Assign project to employee</a></li>
<li><a href="#get-all-projects">Get all projects</a></li>
<li><a href="#update-project">Update project</a></li>
<li><a href="#delete-project">Delete project</a></li>
</ul>
</li>
<li><a href="#employee-endpoint">Employee endpoint</a>
<ul class="sectlevel3">
<li><a href="#create-new-employee">Create new employee</a></li>
<li><a href="#assign-employee-to-project">Assign employee to project</a></li>
<li><a href="#get-all-employees">Get all employees</a></li>
<li><a href="#update-employee">Update employee</a></li>
<li><a href="#delete-employee">Delete employee</a></li>
</ul>
</li>
<li><a href="#client-endpoint">Client endpoint</a>
<ul class="sectlevel3">
<li><a href="#create-new-client">Create new client</a></li>
<li><a href="#get-all-clients">Get all clients</a></li>
<li><a href="#update-client">Update client</a></li>
<li><a href="#delete-client">Delete client</a></li>
</ul>
</li>
</ul>
</div>
</div>
<div id="content">
<div class="sect1">
<h2 id="Overview"><a class="link" href="#Overview">Overview</a></h2>
<div class="sectionbody">
<div class="paragraph">
<p><strong>REST API for comparative project management</strong></p>
</div>
<div class="paragraph">
<p>Main features:</p>
</div>
<div class="ulist">
<ul>
<li>
<p>Create projects</p>
</li>
<li>
<p>Create clients</p>
</li>
<li>
<p>Create employees</p>
</li>
<li>
<p>Assign projects to client</p>
</li>
<li>
<p>Assign projects to employee</p>
</li>
<li>
<p>Assign employees to project</p>
</li>
</ul>
</div>
<div class="paragraph">
<p>Other features:</p>
</div>
<div class="ulist">
<ul>
<li>
<p>Get all : Clients, Employees, Projects</p>
</li>
<li>
<p>Update by id : Clients, Employees, Projects</p>
</li>
<li>
<p>Delete by id: Clients, Employees, Projects</p>
</li>
</ul>
</div>
</div>
</div>
<div class="sect1">
<h2 id="project-endpoint"><a class="link" href="#project-endpoint">Project endpoint</a></h2>
<div class="sectionbody">
<div class="sect3">
<h4 id="create-new-project"><a class="link" href="#create-new-project">Create new project</a></h4>
<div class="paragraph">
<p><code>POST</code></p>
</div>
<div class="ulist">
<ul>
<li>
<p>Request structure</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>POST /api/v1/projects HTTP/1.1
Content-Type: application/json;charset=UTF-8
Content-Length: 135
Host: localhost:8080

{"projectId":1,"projectName":"awesomeProject","startDate":"2021-04-18T18:21:58.110+00:00","finishDate":"2021-04-18T18:21:58.110+00:00"}</pre>
</div>
</div>
<div class="ulist">
<ul>
<li>
<p>Example response</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 135

{"projectId":1,"projectName":"awesomeProject","startDate":"2021-04-18T18:21:58.110+00:00","finishDate":"2021-04-18T18:21:58.110+00:00"}</pre>
</div>
</div>
<div class="ulist">
<ul>
<li>
<p>CURL request</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>$ curl 'http://localhost:8080/api/v1/projects' -i -X POST \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -d '{"projectId":1,"projectName":"awesomeProject","startDate":"2021-04-18T18:21:58.110+00:00","finishDate":"2021-04-18T18:21:58.110+00:00"}'</pre>
</div>
</div>
</div>
<div class="sect3">
<h4 id="assign-project-to-client"><a class="link" href="#assign-project-to-client">Assign project to client</a></h4>
<div class="paragraph">
<p><code>GET</code> /{id}/assign-to-client/{clientId}</p>
</div>
<div class="ulist">
<ul>
<li>
<p>Request structure</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>GET /api/v1/projects/1/assign-to-client/1 HTTP/1.1
Content-Type: application/json;charset=UTF-8
Host: localhost:8080</pre>
</div>
</div>
<div class="ulist">
<ul>
<li>
<p>Example response</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 135

{"projectId":1,"projectName":"awesomeProject","startDate":"2021-04-18T18:22:00.049+00:00","finishDate":"2021-04-18T18:22:00.049+00:00"}</pre>
</div>
</div>
<div class="ulist">
<ul>
<li>
<p>CURL request</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>$ curl 'http://localhost:8080/api/v1/projects/1/assign-to-client/1' -i -X GET \
    -H 'Content-Type: application/json;charset=UTF-8'</pre>
</div>
</div>
</div>
<div class="sect3">
<h4 id="assign-project-to-employee"><a class="link" href="#assign-project-to-employee">Assign project to employee</a></h4>
<div class="paragraph">
<p><code>GET</code> /{id}/assign-to-employee/{employeeId}</p>
</div>
<div class="ulist">
<ul>
<li>
<p>Request structure</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>GET /api/v1/projects/1/assign-to-employee/1 HTTP/1.1
Content-Type: application/json;charset=UTF-8
Host: localhost:8080</pre>
</div>
</div>
<div class="ulist">
<ul>
<li>
<p>Example response</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 135

{"projectId":1,"projectName":"awesomeProject","startDate":"2021-04-18T18:21:55.997+00:00","finishDate":"2021-04-18T18:21:55.997+00:00"}</pre>
</div>
</div>
<div class="ulist">
<ul>
<li>
<p>CURL request</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>$ curl 'http://localhost:8080/api/v1/projects/1/assign-to-employee/1' -i -X GET \
    -H 'Content-Type: application/json;charset=UTF-8'</pre>
</div>
</div>
</div>
<div class="sect3">
<h4 id="get-all-projects"><a class="link" href="#get-all-projects">Get all projects</a></h4>
<div class="paragraph">
<p><code>GET</code></p>
</div>
<div class="ulist">
<ul>
<li>
<p>Request structure</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>GET /api/v1/projects HTTP/1.1
Host: localhost:8080</pre>
</div>
</div>
<div class="ulist">
<ul>
<li>
<p>Example response</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 423

[{"projectId":1,"projectName":"awesomeProject","startDate":"2021-04-18T18:21:54.998+00:00","finishDate":"2021-04-18T18:21:54.998+00:00"},{"projectId":2,"projectName":"anotherAwesomeProject","startDate":"2021-04-18T18:21:54.998+00:00","finishDate":"2021-04-18T18:21:54.998+00:00"},{"projectId":3,"projectName":"oneMoreAwesomeProject","startDate":"2021-04-18T18:21:54.998+00:00","finishDate":"2021-04-18T18:21:54.998+00:00"}]</pre>
</div>
</div>
<div class="ulist">
<ul>
<li>
<p>CURL request</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>$ curl 'http://localhost:8080/api/v1/projects' -i -X GET</pre>
</div>
</div>
</div>
<div class="sect3">
<h4 id="update-project"><a class="link" href="#update-project">Update project</a></h4>
<div class="paragraph">
<p><code>PUT</code> /{id}</p>
</div>
<div class="ulist">
<ul>
<li>
<p>Request structure</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>PUT /api/v1/projects/1 HTTP/1.1
Content-Type: application/json;charset=UTF-8
Content-Length: 135
Host: localhost:8080

{"projectId":1,"projectName":"newProjectName","startDate":"2021-04-18T18:21:57.017+00:00","finishDate":"2021-04-18T18:21:57.017+00:00"}</pre>
</div>
</div>
<div class="ulist">
<ul>
<li>
<p>Example response</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 135

{"projectId":1,"projectName":"newProjectName","startDate":"2021-04-18T18:21:57.017+00:00","finishDate":"2021-04-18T18:21:57.017+00:00"}</pre>
</div>
</div>
<div class="ulist">
<ul>
<li>
<p>CURL request</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>$ curl 'http://localhost:8080/api/v1/projects/1' -i -X PUT \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -d '{"projectId":1,"projectName":"newProjectName","startDate":"2021-04-18T18:21:57.017+00:00","finishDate":"2021-04-18T18:21:57.017+00:00"}'</pre>
</div>
</div>
</div>
<div class="sect3">
<h4 id="delete-project"><a class="link" href="#delete-project">Delete project</a></h4>
<div class="paragraph">
<p><code>DELETE</code> /{id}</p>
</div>
<div class="ulist">
<ul>
<li>
<p>Request structure</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>DELETE /api/v1/projects/1 HTTP/1.1
Host: localhost:8080</pre>
</div>
</div>
<div class="ulist">
<ul>
<li>
<p>Example response</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>HTTP/1.1 200 OK</pre>
</div>
</div>
<div class="ulist">
<ul>
<li>
<p>URL request</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>$ curl 'http://localhost:8080/api/v1/projects/1' -i -X DELETE</pre>
</div>
</div>
</div>
</div>
</div>
<div class="sect1">
<h2 id="employee-endpoint"><a class="link" href="#employee-endpoint">Employee endpoint</a></h2>
<div class="sectionbody">
<div class="paragraph">
<p>api/v1/employees</p>
</div>
<div class="sect3">
<h4 id="create-new-employee"><a class="link" href="#create-new-employee">Create new employee</a></h4>
<div class="paragraph">
<p><code>POST</code></p>
</div>
<div class="ulist">
<ul>
<li>
<p>Request structure</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>POST /api/v1/employees HTTP/1.1
Content-Type: application/json;charset=UTF-8
Content-Length: 121
Host: localhost:8080

{"employeeId":1,"firstName":"John","lastName":"Doe","email":"jonh.doe@company.com","pesel":"12345678901","projects":null}</pre>
</div>
</div>
<div class="ulist">
<ul>
<li>
<p>Example response</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 121

{"employeeId":1,"firstName":"John","lastName":"Doe","email":"jonh.doe@company.com","pesel":"12345678901","projects":null}</pre>
</div>
</div>
<div class="ulist">
<ul>
<li>
<p>CURL request</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>$ curl 'http://localhost:8080/api/v1/employees' -i -X POST \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -d '{"employeeId":1,"firstName":"John","lastName":"Doe","email":"jonh.doe@company.com","pesel":"12345678901","projects":null}'</pre>
</div>
</div>
</div>
<div class="sect3">
<h4 id="assign-employee-to-project"><a class="link" href="#assign-employee-to-project">Assign employee to project</a></h4>
<div class="paragraph">
<p><code>GET</code> /{id}/assign/{projectId}</p>
</div>
<div class="ulist">
<ul>
<li>
<p>Request structure</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>GET /api/v1/employees/1/assign/1 HTTP/1.1
Content-Type: application/json;charset=UTF-8
Host: localhost:8080</pre>
</div>
</div>
<div class="ulist">
<ul>
<li>
<p>Example response</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 251

{"employeeId":1,"firstName":"John","lastName":"Doe","email":"jonh.doe@company.com","pesel":"12345678901","projects":[{"projectId":1,"projectName":"CoolProject","startDate":"2021-04-18T18:21:42.831+00:00","finishDate":"2021-04-18T18:21:42.831+00:00"}]}</pre>
</div>
</div>
<div class="ulist">
<ul>
<li>
<p>CURL request</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>$ curl 'http://localhost:8080/api/v1/employees/1/assign/1' -i -X GET \
    -H 'Content-Type: application/json;charset=UTF-8'</pre>
</div>
</div>
</div>
<div class="sect3">
<h4 id="get-all-employees"><a class="link" href="#get-all-employees">Get all employees</a></h4>
<div class="paragraph">
<p><code>GET</code></p>
</div>
<div class="ulist">
<ul>
<li>
<p>Request structure</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>GET /api/v1/employees HTTP/1.1
Host: localhost:8080</pre>
</div>
</div>
<div class="ulist">
<ul>
<li>
<p>Example response</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 365

[{"employeeId":1,"firstName":"John","lastName":"Doe","email":"jonh.doe@company.com","pesel":"12345678901","projects":[]},{"employeeId":2,"firstName":"Albert","lastName":"Doe","email":"albert.doe@company.com","pesel":"12345678902","projects":[]},{"employeeId":3,"firstName":"Jane","lastName":"Doe","email":"jane.doe@company.com","pesel":"12345678903","projects":[]}]</pre>
</div>
</div>
<div class="ulist">
<ul>
<li>
<p>CURL request</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>$ curl 'http://localhost:8080/api/v1/employees' -i -X GET</pre>
</div>
</div>
</div>
<div class="sect3">
<h4 id="update-employee"><a class="link" href="#update-employee">Update employee</a></h4>
<div class="paragraph">
<p><code>PUT</code> /{id}</p>
</div>
<div class="ulist">
<ul>
<li>
<p>Request structure</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>PUT /api/v1/employees/1 HTTP/1.1
Content-Type: application/json;charset=UTF-8
Content-Length: 133
Host: localhost:8080

{"employeeId":1,"firstName":"changedFirstName","lastName":"Doe","email":"jonh.doe@company.com","pesel":"12345678901","projects":null}</pre>
</div>
</div>
<div class="ulist">
<ul>
<li>
<p>Example response</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 133

{"employeeId":1,"firstName":"changedFirstName","lastName":"Doe","email":"jonh.doe@company.com","pesel":"12345678901","projects":null}</pre>
</div>
</div>
<div class="ulist">
<ul>
<li>
<p>CURL request</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>$ curl 'http://localhost:8080/api/v1/employees/1' -i -X PUT \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -d '{"employeeId":1,"firstName":"changedFirstName","lastName":"Doe","email":"jonh.doe@company.com","pesel":"12345678901","projects":null}'</pre>
</div>
</div>
</div>
<div class="sect3">
<h4 id="delete-employee"><a class="link" href="#delete-employee">Delete employee</a></h4>
<div class="paragraph">
<p><code>DELETE</code> /{id}</p>
</div>
<div class="ulist">
<ul>
<li>
<p>Request structure</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>DELETE /api/v1/employees/1 HTTP/1.1
Host: localhost:8080</pre>
</div>
</div>
<div class="ulist">
<ul>
<li>
<p>Example response</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>HTTP/1.1 200 OK</pre>
</div>
</div>
<div class="ulist">
<ul>
<li>
<p>URL request</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>$ curl 'http://localhost:8080/api/v1/employees/1' -i -X DELETE</pre>
</div>
</div>
</div>
</div>
</div>
<div class="sect1">
<h2 id="client-endpoint"><a class="link" href="#client-endpoint">Client endpoint</a></h2>
<div class="sectionbody">
<div class="paragraph">
<p>api/v1/clients</p>
</div>
<div class="sect3">
<h4 id="create-new-client"><a class="link" href="#create-new-client">Create new client</a></h4>
<div class="paragraph">
<p><code>POST</code></p>
</div>
<div class="ulist">
<ul>
<li>
<p>Request structure</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>POST /api/v1/clients HTTP/1.1
Content-Type: application/json;charset=UTF-8
Content-Length: 51
Host: localhost:8080

{"clientId":1,"companyName":"SpaceX","projects":[]}</pre>
</div>
</div>
<div class="ulist">
<ul>
<li>
<p>Example response</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>HTTP/1.1 201 Created
Content-Type: application/json
Content-Length: 51

{"clientId":1,"companyName":"SpaceX","projects":[]}</pre>
</div>
</div>
<div class="ulist">
<ul>
<li>
<p>CURL request</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>$ curl 'http://localhost:8080/api/v1/clients' -i -X POST \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -d '{"clientId":1,"companyName":"SpaceX","projects":[]}'</pre>
</div>
</div>
</div>
<div class="sect3">
<h4 id="get-all-clients"><a class="link" href="#get-all-clients">Get all clients</a></h4>
<div class="paragraph">
<p><code>GET</code></p>
</div>
<div class="ulist">
<ul>
<li>
<p>Request structure</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>GET /api/v1/clients HTTP/1.1
Host: localhost:8080</pre>
</div>
</div>
<div class="ulist">
<ul>
<li>
<p>Example response</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 208

[{"clientId":1,"companyName":"SpaceX","projects":[]},{"clientId":2,"companyName":"Tesla","projects":[]},{"clientId":3,"companyName":"StarLink","projects":[]},{"clientId":4,"companyName":"Nasa","projects":[]}]</pre>
</div>
</div>
<div class="ulist">
<ul>
<li>
<p>CURL request</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>$ curl 'http://localhost:8080/api/v1/clients' -i -X GET</pre>
</div>
</div>
</div>
<div class="sect3">
<h4 id="update-client"><a class="link" href="#update-client">Update client</a></h4>
<div class="paragraph">
<p><code>PUT</code> /{id}</p>
</div>
<div class="ulist">
<ul>
<li>
<p>Request structure</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>PUT /api/v1/clients/1 HTTP/1.1
Content-Type: application/json;charset=UTF-8
Content-Length: 59
Host: localhost:8080

{"clientId":1,"companyName":"newCompanyName","projects":[]}</pre>
</div>
</div>
<div class="ulist">
<ul>
<li>
<p>Example response</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 59

{"clientId":1,"companyName":"newCompanyName","projects":[]}</pre>
</div>
</div>
<div class="ulist">
<ul>
<li>
<p>CURL request</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>$ curl 'http://localhost:8080/api/v1/clients/1' -i -X PUT \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -d '{"clientId":1,"companyName":"newCompanyName","projects":[]}'</pre>
</div>
</div>
</div>
<div class="sect3">
<h4 id="delete-client"><a class="link" href="#delete-client">Delete client</a></h4>
<div class="paragraph">
<p><code>DELETE</code> /{id}</p>
</div>
<div class="ulist">
<ul>
<li>
<p>Request structure</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>DELETE /api/v1/clients/1 HTTP/1.1
Host: localhost:8080</pre>
</div>
</div>
<div class="ulist">
<ul>
<li>
<p>Example response</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>HTTP/1.1 200 OK</pre>
</div>
</div>
<div class="ulist">
<ul>
<li>
<p>URL request</p>
</li>
</ul>
</div>
<div class="listingblock">
<div class="content">
<pre>$ curl 'http://localhost:8080/api/v1/clients/1' -i -X DELETE</pre>
</div>
</div>
</div>
</div>
</div>
</div>
<div id="footer">
<div id="footer-text">
Last updated 2021-04-18 20:20:59 CEST
</div>
</div>
</body>
</html>