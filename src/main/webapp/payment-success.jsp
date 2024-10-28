<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Payment Success</title>
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            background-color: #f0f2f5;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            color: #333;
        }

        .success-message {
            background-color: #ffffff;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            text-align: center;
            max-width: 400px;
            width: 90%;
            animation: fadeIn 0.8s ease;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(-10px); }
            to { opacity: 1; transform: translateY(0); }
        }

        h1 {
            color: #28a745;
            font-size: 1.8em;
            margin-bottom: 10px;
        }

        p {
            font-size: 1em;
            line-height: 1.5;
            color: #555;
            margin: 10px 0;
        }

        .amount {
            font-size: 2em;
            color: #28a745;
            font-weight: 700;
            margin: 15px 0;
        }

        .redirect-message {
            font-size: 0.9em;
            color: #777;
        }
    </style>
</head>
<body>
<div class="success-message">
    <h1>Payment Successful!</h1>
    <p>Thank you for your purchase of:</p>
    <p class="amount">$<span>${amount}</span></p>
    <p class="redirect-message">You will be redirected to your orders page shortly.</p>
</div>

<script>
    // Redirect to the orders page after 5 seconds
    setTimeout(function() {
        window.location.href = '/buyers/orders';
    }, 5000);
</script>
</body>
</html>
