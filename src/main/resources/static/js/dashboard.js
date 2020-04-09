var modal = $(".modal-container");

$(window).click(function(e) {
    if (e.target.className == "modal-container") {
        $("#modal-container").hide();
    }
});

function closeModal() {
     $("#modal-container").hide();
}

function handleDeleteChild(childId) {
    console.log(childId);
    let childRow = $('#child-row' + childId);
    $.ajax({
        url: '/admin/remove-child/',
        type: 'DELETE',
        data: {"childId": childId},
        success: function(childInfoResponse) {
            childRow.addClass("deleted-row-styling");
        },
        error : function() {
        // TODO: Error handling.
            console.log("error");
        }
    });
}

function handleAddChildClick() {
     $.ajax({
        url: '/admin/retrieve-add-child/',
        type: 'GET',
        data: {
            "mode": 'add'
        },
        success : function(response) {
            $('#modify-child-modal-holder').html(response)
            $("#modal-container").show();
        },
        error : function() {
        }
    });
}



function handleEditChildClick(childId, mode) {
    $.ajax({
        url: '/admin/retrieve-edit-child/',
        type: 'GET',
        data: {
            "childId": childId,
            "mode": mode
        },
        success : function(response) {
            $('#modify-child-modal-holder').html(response)
            $("#modal-container").show();
        },
        error : function() {
        }
    });
}