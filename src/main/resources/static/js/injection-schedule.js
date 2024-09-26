function getSelectedInjectionSchedule() {
    let selected = [];
    let checkboxes = document.querySelectorAll('input[name="selectInjectionSchedule"]:checked');
    checkboxes.forEach(checkbox => selected.push(checkbox.value));
    return selected;
}


function makeUpdateInjectionSchedule() {
    let selectedInjectionSchedule = getSelectedInjectionSchedule();
    if (selectedInjectionSchedule.length === 0) {
        alert("Please select a injection schedule to update.");
        return;
    }

    if (selectedInjectionSchedule.length > 1) {
        alert("You can only update one injection schedule at a time.");
        return;
    }

    window.location.href = `/admin/injection-schedule/update/${selectedInjectionSchedule[0]}`;
}