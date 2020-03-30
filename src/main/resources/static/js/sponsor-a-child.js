
/**
* Updates the checkbox's style depending on whether the child was selected or not..
*/
function onChildSelection(childId) {
  let selectedClass = "selected-overlay";
  let elem = $("#child-id" + childId);
  elem.hasClass(selectedClass) ? elem.removeClass(selectedClass) : elem.addClass(selectedClass);
}


