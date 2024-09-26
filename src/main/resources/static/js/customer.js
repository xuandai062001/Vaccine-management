function getSelectedCustomers() {
    let selected = [];
    let checkboxes = document.querySelectorAll('input[name="selectedCustomer"]:checked');
    checkboxes.forEach(checkbox => selected.push(checkbox.value));
    return selected;
}




function deleteCustomers() {
    let selectedCustomers = getSelectedCustomers();
    if (selectedCustomers.length === 0) {
        alert("Please select at least one customer to delete.");
        return;
    }

    if (confirm("Are you sure you want to delete the selected customers?")) {
        let form = document.createElement("form");
        form.method = "POST";
        form.action = "/admin/customer/delete";

        selectedCustomers.forEach(id => {
            let input = document.createElement("input");
            input.type = "hidden";
            input.name = "userIds";
            input.value = id;
            form.appendChild(input);
        });

        document.body.appendChild(form);
        form.submit();
    }
}


function updateCustomer() {
    let selectedCustomers = getSelectedCustomers();
    if (selectedCustomers.length === 0) {
        alert("Please select a customer to update.");
        return;
    }

    if (selectedCustomers.length > 1) {
        alert("You can only update one customer at a time.");
        return;
    }

    window.location.href = `/admin/customer/update/${selectedCustomers[0]}`;
}