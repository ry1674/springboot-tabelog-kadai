const imageInput = document.getElementById('imageFile');
const imagePreview = document.getElementById('imagePreview');

imageInput.addEventListener('change', () => {
  if (imageInput.files[0]) {
    let fileReader = new FileReader();
    fileReader.onload = () => {
      imagePreview.innerHTML = `<img src="${fileReader.result}" class="mb-3">`;
    }
    fileReader.readAsDataURL(imageInput.files[0]);
  } else {
    imagePreview.innerHTML = '';
  }
})

document.addEventListener("DOMContentLoaded", function() {
  // ページがロードされたときに現在の状態をセットアップ
  change();
});

function change() {
  var checkboxes = [
    document.getElementById("checkbox1"),
    document.getElementById("checkbox2"),
    document.getElementById("checkbox3"),
    document.getElementById("checkbox4"),
    document.getElementById("checkbox5"),
    document.getElementById("checkbox6"),
    document.getElementById("checkbox7")
  ];

  var irregularClosedDayCheckbox = document.getElementById("irregularClosedDay");

  // 初期状態設定: 不定休と各曜日の選択状態を確認し、設定を反映
  var isIrregularChecked = irregularClosedDayCheckbox.checked;
  var anyChecked = checkboxes.some(function(checkbox) {
    return checkbox.checked;
  });

  if (isIrregularChecked) {
    checkboxes.forEach(function(checkbox) {
      checkbox.disabled = true;
    });
  } else if (anyChecked) {
    irregularClosedDayCheckbox.disabled = true;
  } else {
    checkboxes.forEach(function(checkbox) {
      checkbox.disabled = false;
    });
    irregularClosedDayCheckbox.disabled = false;
  }
}
