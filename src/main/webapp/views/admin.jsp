<%@ include file="/WEB-INF/views/include.jsp" %>

<c:url value="/admin/create" var="createUrl"/>
<c:url value="/admin/update" var="editUrl"/>
<c:url value="/admin/delete" var="deleteUrl"/>

<html>
<head><title>Poliana API - Users</title></head>


<script type='text/javascript' src='<c:url value="/resources/js/customUsers.js"/>'></script>

<title>User</title>

<script type='text/javascript'>

    $(function() {
        // init
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

<div id='newForm'>
    <form>
        <fieldset>
            <legend>Create New User</legend>
            <label for='newUsername'>Username</label><input type='text' id='newUsername'/><br/>
            <label for='newPassword'>Password</label><input type='password' id='newPassword'/><br/>
            <label for='newFirstName'>First Name</label><input type='text' id='newFirstName'/><br/>
            <label for='newLastName'>Last Name</label><input type='text' id='newLastName'/><br/>

            <label for='newRole'>Role</label>
            <select id='newRole'>
                <option value='1' selected='selected'>User</option>
                <option value='2'>Research</option>
            </select>

        </fieldset>

        <input type='submit' value='Submit'/>
    </form>
</div>


</body>
</html>
