"use client";

import { Button, TextField, Typography, CircularProgress, Alert } from "@mui/material";
import { useState } from "react";
import { LocalizationProvider} from "@mui/x-date-pickers";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import dayjs from "dayjs";

export default function Home() {

  const [invoiceDate, setInvoiceDate] = useState("");
  const [loading, setLoading] = useState(false);

  const [currency, setCurrency] = useState("");

  const [total, setTotal] = useState("");
  const [error, setError] = useState("");

  const [lines, setLines] = useState([
  {
    description: "",
    amount: "",
    currency: ""
  }
]);

  const addLine = () => {

  setLines([
    ...lines,
    {
      description: "",
      amount: "",
      currency: ""
    }
  ]);
};

const calculateTotal = async () => {
  console.log("Button clicked");
  setLoading(true);


  try {
    const response = await fetch("http://localhost:8080/invoice/total", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        invoice: {
          currency,
          date: invoiceDate,
          lines,
        },
      }),
    });

    const result = await response.text();

    if (!response.ok) {
      // Backend returns "Error: ..." for 400/404/500
      setError(result);
    } else {
      setTotal(result);
    }
  } catch (err) {
    // Network failure, CORS error, backend not running
    setError("Could not reach the server. Is the backend running on port 8080?");
  } finally {
    setLoading(false);
  }
};

  return (
    <div style={{ padding: 20 }}>

      <Typography variant="h4">
          Invoice form {total}
      </Typography>

      <LocalizationProvider dateAdapter={AdapterDayjs}>

  <DatePicker
    label="Invoice Date"
    value={invoiceDate ? dayjs(invoiceDate) : null}
    onChange={(newValue) => {

      if (newValue) {
        setInvoiceDate(
          dayjs(newValue).format("MM-DD-YYYY")
        );
      } else {
        setInvoiceDate("");
      }
    }}
    slotProps={{
      textField: {
        fullWidth: true,
        margin: "normal"
      }
    }}
  />
</LocalizationProvider>

      <TextField
  label="Base Currency"
  value={currency}
  onChange={(e) =>
    setCurrency(e.target.value)}
/>
    {lines.map((line, index) => (

      <div key={index}>

        <TextField
  label="Description"
  margin="normal"
  value={line.description}
  onChange={(e) => {

    const updatedLines = [...lines];

    updatedLines[index].description =
      e.target.value;

    setLines(updatedLines);
  }}
/>

<TextField
  label="Amount"
  margin="normal"
  value={line.amount}
  style={{ marginLeft: "10px" }}
  onChange={(e) => {

    const updatedLines = [...lines];

    updatedLines[index].amount =
      e.target.value;

    setLines(updatedLines);
  }}
/>

<TextField
  label="Currency"
  margin="normal"
  value={line.currency}
  style={{ marginLeft: "10px" }}
  onChange={(e) => {

    const updatedLines = [...lines];

    updatedLines[index].currency =
      e.target.value;

    setLines(updatedLines);
  }}
/>

      </div>
    ))}

      <Button
  variant="outlined"
  onClick={addLine}
>
  Add Line
</Button>

<Button
  variant="contained"
  onClick={calculateTotal}
  disabled={loading}
  style={{ marginLeft: "10px" }}
>
  {loading ? <CircularProgress size={20} /> : "Calculate Total"}
</Button>
{total && <Alert severity="success">Total: {total}</Alert>}
{error && <Alert severity="error">{error}</Alert>}


    </div>
  );
}