let maxDate = new Date();
maxDate.setMonth(maxDate.getMonth() + 3);

var openTime = document.getElementById('openTime');
var minTime = openTime.value;

var closeTime = document.getElementById('closeTime');
var maxTime = closeTime.value;

// HTML要素からカンマ区切りの曜日を取得する
var closedDays = document.getElementById('closedDay').value;  // '月曜日,火曜日' など

// 曜日の対応表（日本語 -> 数字）
const daysMapping = {
  '日曜日': 0,
  '月曜日': 1,
  '火曜日': 2,
  '水曜日': 3,
  '木曜日': 4,
  '金曜日': 5,
  '土曜日': 6
};

// カンマで分割して曜日文字列の配列に変換
var closedDaysArray = closedDays.split(',').map(day => day.trim());

// flatpickr 初期化
flatpickr('#reservationDateTime', {
  locale: 'ja',
  enableTime: true,
  dateFormat: "Y/m/d H:i",
  disable: [
    function(date) {
      // 現在の日付が無効化する曜日の一つであるかを確認
      return closedDaysArray.some(closedDay => {
        const closedDayNumber = daysMapping[closedDay];
        return date.getDay() === closedDayNumber;
      });
    }
  ],
  minDate: 'today',
  minTime: minTime,
  maxTime: maxTime,
  maxDate: maxDate
});
