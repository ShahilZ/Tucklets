function handleUploadFile() {
    // Prevents null submission
    $('.upload-button').prop('disabled', !$('.upload-file-button').val());
}

function preventMultipleSubmissions() {
     $('.upload-button').prop('disabled', true);
}