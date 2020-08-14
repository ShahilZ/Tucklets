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
    let childRow = $('#child-row' + childId);
    $.ajax({
        url: '/admin/children-dashboard/remove-child',
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
        url: '/admin/children-dashboard/add-child/',
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
        url: '/admin/children-dashboard/edit-child/',
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
        url: '/admin/children-dashboard/export-pdf',
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

/**
* Method that helps with uploading a child's image up to the server.
*/
function uploadChildImage(childId) {
    console.log(childId);
    let reader = new FileReader();
    let imageElem = $('#child-image-' + childId)[0];
    let imageInput = $('#upload-image-' + childId)[0];
    reader.onload = function (e) {
        // get loaded data and render thumbnail.
       imageElem.src = e.target.result;
    };
    // read the image file as a data URL.
    reader.readAsDataURL(imageInput.files[0]);

}

/**
* Handler for changing a child's image. Triggers the file input.
*/
function handleChangeChildImage(elementId) {
    $('#' + elementId).click();
}