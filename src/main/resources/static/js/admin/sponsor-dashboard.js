var modal = $(".modal-container");

$(window).click(function(e) {
    if (e.target.className == "modal-container") {
        $("#modal-container").hide();
    }
});

function closeModal() {
     $("#modal-container").hide();
}

function handleDeleteSponsor(sponsorId) {
    console.log(sponsorId);
    let sponsorRow = $('#sponsor-row' + sponsorId);
    $.ajax({
        url: '/admin/sponsor-dashboard/remove-sponsor/',
        type: 'DELETE',
        data: {"sponsorId": sponsorId},
        success: function(sponsorInfoResponse) {
            sponsorRow.addClass("deleted-row-styling");
        },
        error : function() {
        // TODO: Error handling.
            console.log("error");
        }
    });
}

function handleAddSponsorClick() {
     $.ajax({
        url: '/admin/sponsor-dashboard/add-sponsor',
        type: 'GET',
        success : function(response) {
            $('#modify-sponsor-modal-holder').html(response)
            $("#modal-container").show();
            $("#add_title").show();
            $("#add_button").show();
            $("#edit_title").hide();
            $("#edit_button").hide();
        },
        error : function() {
            console.log("error");
        }
    });
}

function handleEditSponsorClick(sponsorId) {
    $.ajax({
        url: '/admin/sponsor-dashboard/edit-sponsor/',
        type: 'GET',
        data: {"sponsorId": sponsorId},
        success : function(response) {
            $('#modify-sponsor-modal-holder').html(response)
            $("#modal-container").show();
            $("#edit_title").show();
            $("#edit_button").show();
            $("#add_title").hide();
            $("#add_button").hide();
        },
        error : function() {
            console.log("error");
        }
    });
}