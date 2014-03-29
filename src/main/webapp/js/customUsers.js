var urlHolder = new Object();

function submitGetApiKey() {

    $.post(urlHolder.key, {
            username: $('#username').val(),
            password: $('#password').val()
        },
        function(response) {
            if (response != null) {

                alert(response.toString());
            } else {
                alert('Failure! An error has occurred!');
            }
        },
        "json"
    );
}

function submitNewUser() {

    $.post(urlHolder.new, {
            username: $('#newUsername').val(),
            password: $('#newPassword').val(),
            firstName: $('#newFirstName').val(),
            lastName: $('#newLastName').val()
        },

        function(response) {
            if (response != null) {

                alert(response.toString());
            } else {
                alert('Failure! An error has occurred!');
            }
        }
    );
}

function submitUpdateRecord() {
    $.post(urlHolder.edit, {
            username: $('#editUsername').val(),
            firstName: $('#editFirstName').val(),
            lastName: $('#editLastName').val(),
            role: $('#editRole').val()
        },
        function(response) {
            if (response != null) {
                loadTable();
                toggleForms('hide'); ;
                toggleCrudButtons('show');
                alert('Success! Record has been edited.');
            } else {
                alert('Failure! An error has occurred!');
            }
        }
    );
}

function submitDeleteRecord() {
    var selected = $('input:radio[name=index]:checked').val();

    $.post(urlHolder.del, {
            username: $('#tableUsers').data('model')[selected].username
        },
        function(response) {
            if (response == true) {
                loadTable(urlHolder.records);
                alert('Success! Record has been deleted.');
            } else {
                alert('Failure! An error has occurred!');
            }
        }
    );
}
