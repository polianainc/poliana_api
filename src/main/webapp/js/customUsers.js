var urlHolder = new Object();

function submitGetApiKey() {
    $.get(urlHolder.add, {
            username: $('#username').val(),
            password: $('#password').val()
        },
        function(response) {
            if (response != null) {
                loadTable();
                toggleForms('hide'); ;
                toggleCrudButtons('show');
                alert('Success! Record has been added.');
            } else {
                alert('Failure! An error has occurred!');
            }
        }
    );
}

function submitNewUser() {
    $.post(urlHolder.add, {
            username: $('#newUsername').val(),
            password: $('#newPassword').val(),
            firstName: $('#newFirstName').val(),
            lastName: $('#newLastName').val(),
            role: $('#newRole').val()
        },
        function(response) {
            if (response != null) {
                loadTable();
                toggleForms('hide'); ;
                toggleCrudButtons('show');
                alert('Success! Record has been added.');
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
