function deposit() {
  const accNo = document.getElementById("accountNo").value;
  const amount = document.getElementById("amount").value;

  fetch("/deposit", {
    method: "POST",
    headers: { "Content-Type": "application/x-www-form-urlencoded" },
    body: `accountNo=${accNo}&amount=${amount}`
  })
  .then(response => response.text())
  .then(data => {
    document.getElementById("message").innerText = data;
  })
  .catch(err => console.error(err));
}

function showBalance() {
  const accNo = prompt("Enter your account number:");
  fetch(`/balance?accountNo=${accNo}`)
    .then(res => res.text())
    .then(data => {
      document.getElementById("output").innerText = "Balance: ₹" + data;
    });
}

function showTransactions() {
  const accNo = prompt("Enter your account number:");
  fetch(`/transactions?accountNo=${accNo}`)
    .then(res => res.text())
    .then(data => {
      document.getElementById("output").innerHTML = "<pre>" + data + "</pre>";
    });
}
