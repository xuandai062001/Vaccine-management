function getSelectedVaccine() {
    let selected = [];
    let checkboxes = document.querySelectorAll('input[name="selectVaccineType"]:checked');
    checkboxes.forEach(checkbox => selected.push(checkbox.value));
    return selected;
}



function makeInActiveVaccineType() {
    const checkboxes = document.querySelectorAll('input[name="selectVaccineType"]:checked');
    const selectedIds = Array.from(checkboxes).map(checkbox => checkbox.value);

    if (selectedIds.length === 0) {
        alert("Bạn chưa chọn loại vắc xin nào.");
        return;
    }

    // Tạo một form mới để gửi dữ liệu
    const form = document.createElement('form');
    form.method = 'POST';
    form.action = '/admin/vaccine-type/inactive'; // URL đến controller


    // Thêm các ID vào form
    selectedIds.forEach(id => {
        const input = document.createElement('input');
        input.type = 'hidden';
        input.name = 'selectVaccineType'; // Tên tương ứng với @RequestParam trong controller
        input.value = id;
        form.appendChild(input);
    });

    // Gửi form
    document.body.appendChild(form);
    form.submit();
}



function deleteVaccineType() {
    let selectedVaccines = getSelectedVaccine();
    if (selectedVaccines.length === 0) {
        alert("Please select at least one customer to delete.");
        return;
    }


    if (confirm("Are you sure you want to delete the selected vaccinetype?")) {

        let form = document.createElement("form");
        form.method = "POST";
        form.action = "/admin/vaccine-type/delete";

        selectedVaccines.forEach(son => {
            let input = document.createElement("input");
            input.type = "hidden";
            input.name = "Ids";
            input.value = son;
            form.appendChild(input);
        });

        document.body.appendChild(form);
        form.submit();
    }
}



