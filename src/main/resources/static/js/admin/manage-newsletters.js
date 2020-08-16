function handleDeleteNewsletter(newsletterId) {
    let newsletterRow = $('#newsletter-row' + newsletterId);
    $.ajax({
        url: '/admin/newsletters/remove-newsletter',
        type: 'DELETE',
        data: { "newsletterId": newsletterId },
        success: function(newsletterResponse) {
            newsletterRow.addClass("deleted-row-styling");
        },
        error : function() {
        // TODO: Error handling.
            console.log("error");
        }
    });
}

function handleEmailNewsletter() {
    let newsletterMessage = $('.newsletter.hidden-message');
    $.ajax({
        url: '/admin/newsletters/email-latest',
        type: 'PUT',
        success: function(newsletterResponse) {
            newsletterMessage.show();
            let newsletterStatus = JSON.parse(newsletterResponse);
            let newsletterName = newsletterStatus.newsletterName;
            setTimeout(function () {
                newsletterMessage.hide();
            }, 8000);
        },
        error : function(errorResponse) {
        // TODO: Error handling.
            console.log("Error sending newsletter email.");
        }
    });
}