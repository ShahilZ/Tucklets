$(document).ready(function() {
    let selectedChildrenIds = $('#selected-children-ids');
    let isSponsorInfoPage = selectedChildrenIds.length > 0;
    $('.supported-languages-dropdown').change(function () {
        var selectedOption = $('.supported-languages-dropdown').val();
        if (selectedOption != ''){
            if (isSponsorInfoPage) {
                // Add new hidden input for the locale.
                let localeInput = $(document.createElement("input"));
                localeInput.prop("name", "lang");
                localeInput.prop("type", "hidden");
                localeInput.val(selectedOption);
                selectedChildrenIds.append(localeInput);
                selectedChildrenIds.submit();
            }
            else {
                window.location.replace('?lang=' + selectedOption);
            }
        }
    })
});