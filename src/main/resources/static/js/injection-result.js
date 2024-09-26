function getSelectedInjectionResult() {
    let selected = [];
    let checkboxes = document.querySelectorAll('input[name="selectInjectionResult"]:checked');
    checkboxes.forEach(checkbox => selected.push(checkbox.value));
    return selected;
}


function makeDeleteInjectionResult() {
    let selectedInjectionResult = getSelectedInjectionResult();
    if (selectedInjectionResult.length === 0) {
        alert("Please select at least one injection result to delete.");
        return;
    }


    if (confirm("Are you sure you want to delete the selected injection result?")) {

        let form = document.createElement("form");
        form.method = "POST";
        form.action = "/admin/injection-result/delete";

        selectedInjectionResult.forEach(Ids => {
            let input = document.createElement("input");
            input.type = "hidden";
            input.name = "injectionResultIds";
            input.value = Ids;
            form.appendChild(input);
        });

        document.body.appendChild(form);
        form.submit();
    }
}


function makeUpdateInjectionResult() {
    let selectedInjectionResult = getSelectedInjectionResult();
    if (selectedInjectionResult.length === 0) {
        alert("Please select a injection result to update.");
        return;
    }

    if (selectedInjectionResult.length > 1) {
        alert("You can only update one injection result at a time.");
        return;
    }

    window.location.href = `/admin/injection-result/update/${selectedInjectionResult[0]}`;
}