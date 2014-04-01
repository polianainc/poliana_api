<%@ include file="/WEB-INF/views/include.jsp" %>

<c:url value="/user/key" var="keyUrl"/>

<html>
<head><title>Poliana API - Users</title></head>


<script type='text/javascript' src='<c:url value="/resources/js/customUsers.js"/>'></script>

<title>User</title>

<script type='text/javascript'>

    $(function() {
        // init
        urlHolder.key = '${keyUrl}';
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

</body>
</html>
