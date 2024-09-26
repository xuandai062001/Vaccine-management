function getSelectedVaccine() {
    let selected = [];
    let checkboxes = document.querySelectorAll('input[name="selectVaccine"]:checked');
    checkboxes.forEach(checkbox => selected.push(checkbox.value));
    return selected;
}



function makeUpdateVaccine() {
    let selectedVaccine = getSelectedVaccine();
    if (selectedVaccine.length === 0) {
        alert("Please select a vaccine to update.");
        return;
    }

    if (selectedVaccine.length > 1) {
        alert("You can only update one vaccine at a time.");
        return;
    }

    window.location.href = `/admin/vaccine/update/${selectedVaccine[0]}`;
}


function makeInActiveVaccine() {
    const checkboxes = document.querySelectorAll('input[name="selectVaccine"]:checked');
    const selectedIds = Array.from(checkboxes).map(checkbox => checkbox.value);

    if (selectedIds.length === 0) {
        alert("Bạn chưa chọn loại vắc xin nào.");
        return;
    }

    // Tạo một form mới để gửi dữ liệu
    const form = document.createElement('form');
    form.method = 'POST';
    form.action = '/admin/vaccine/inactive'; // URL đến controller


    // Thêm các ID vào form
    selectedIds.forEach(id => {
        const input = document.createElement('input');
        input.type = 'hidden';
        input.name = 'selectVaccine'; // Tên tương ứng với @RequestParam trong controller
        input.value = id;
        form.appendChild(input);
    });

    // Gửi form
    document.body.appendChild(form);
    form.submit();
}
