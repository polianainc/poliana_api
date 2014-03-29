<%@ include file="/WEB-INF/views/include.jsp" %>

<c:url value="/users/key" var="keyUrl"/>
<c:url value="/users/create" var="createUrl"/>
<c:url value="/users/update" var="editUrl"/>
<c:url value="/users/delete" var="deleteUrl"/>

<html>
<head><title>Poliana API - Users</title></head>


<script type='text/javascript' src='<c:url value="/resources/js/customUsers.js"/>'></script>

<title>User</title>

<script type='text/javascript'>

    $(function() {
        // init
        urlHolder.key = '${keyUrl}';
        urlHolder.new = '${createUrl}';
        urlHolder.edit = '${editUrl}';
        urlHolder.del = '${deleteUrl}';

        $('#keyForm').submit(function(e) {
            e.preventDefault();
            submitGetApiKey();
        });

        $('#newForm').submit(function(e) {
            e.preventDefault();
            submitNewUser();
        });

    });
</script>
</head>

<body>


<p id="apikey"></p>

<div id='keyForm'>
    <form>
        <fieldset>
            <legend>Already have an API key? Enter credentials to retrieve it!</legend>
            <input type='hidden' id='editUsername'/>
            <label for='username'>Username</label><input type='text' id='username'/><br/>
            <label for='password'>Password</label><input type='password' id='password'/><br/>
        </fieldset>
        <input type='submit' value='Submit'/>
    </form>
</div>

<p>or</p>

<div id='newForm'>
    <form>
        <fieldset>
            <legend>Create New User</legend>
            <label for='newUsername'>Username</label><input type='text' id='newUsername'/><br/>
            <label for='newPassword'>Password</label><input type='password' id='newPassword'/><br/>
            <label for='newFirstName'>First Name</label><input type='text' id='newFirstName'/><br/>
            <label for='newLastName'>Last Name</label><input type='text' id='newLastName'/><br/>
        </fieldset>
        <input type='submit' value='Submit'/>
    </form>
</div>


</body>
</html>
