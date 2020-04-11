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
        success : function(response) {
            $('#modify-child-modal-holder').html(response)
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


function handleEditChildClick(childId) {
    $.ajax({
        url: '/admin/retrieve-edit-child/',
        type: 'GET',
        data: {"childId": childId},
        success : function(response) {
            $('#modify-child-modal-holder').html(response)
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

function exportChildren() {
    console.log("Exporting");
    $.ajax({
        url: '/admin/dashboard/export-pdf',
        success: function(data) {
        let binaryString = window.atob(data);
        let dataLength = binaryString.length;
        let dataBytes = new Uint8Array(dataLength);

        for (let i = 0; i < dataLength; i++) {
            let ascii = binaryString.charCodeAt(i);
            dataBytes[i] = ascii;
        }
        let blob = new Blob([dataBytes], {type: 'application/pdf'});
        var link=document.createElement('a');
        link.href=window.URL.createObjectURL(blob);
        link.download="children-printout.pdf";
        link.click();
      }
    });
}