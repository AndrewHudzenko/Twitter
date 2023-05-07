<#--<#include "security.ftlh">-->
<#import "login.ftlh" as l>

<nav class="navbar navbar-expand-lg bg-body-tertiar y">
    <div class="container-fluid">
        <a class="navbar-brand" href="/">Twitter</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="/">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/main">Messages</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/user">User list</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/user/profile">Profile</a>
                </li>
            </ul>
<#--            <div class="navbar-text mr-3">${name}</div>-->
            <@l.logout />
        </div>
    </div>
</nav>

