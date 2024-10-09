function showDialog(title, message){
    const dialog = document.getElementById('dialog');
    const dialogTitle = document.getElementById('dialog-title');
    const dialogMessage = document.getElementById('dialog-message');

    console.log("dialog show")
    dialogTitle.textContent = title;
    dialogMessage.textContent = message;
    dialog.style.display = 'block';
}

document.addEventListener('DOMContentLoaded', function () {

    const closeButton = document.querySelector('.close-button');

    closeButton.onclick = function () {
        dialog.style.display = 'none';
    };

    window.onclick = function (event) {
        if (event.target === dialog) {
            dialog.style.display = 'none';
        }
    };

    document.getElementById('confirm-button').onclick = function () {
        dialog.style.display = 'none';
    };
})