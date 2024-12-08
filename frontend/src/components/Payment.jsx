import '../styles/payment.css';
import React, { useState, useEffect } from "react";
import { useLocation } from 'react-router-dom';
import { TbLockCheck } from "react-icons/tb";
import Card from '../img/card.svg';

function Payment() {
  const [activeButton, setActiveButton] = useState(null); // Состояние для отслеживания активной кнопки
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const planFromQuery = parseInt(queryParams.get('plan'), 10); // Извлекаем план из URL
  const [savedPlan, setSavedPlan] = useState(null); 
  const [cardNumber, setCardNumber] = useState('');
  const [expiryMonth, setExpiryMonth] = useState('01'); 
  const [expiryYear, setExpiryYear] = useState('');
  const [securityCode, setSecurityCode] = useState('');
  const [error, setError] = useState(''); 
  const [cardError, setCardError] = useState('');
  const [email, setEmail] = useState('');
  const [country, setCountry] = useState('bl'); 


  useEffect(() => {
    if (planFromQuery === 2 || planFromQuery === 3) {
      setActiveButton(planFromQuery);
    }
  }, [planFromQuery]);

  const handleButtonClick = (buttonIndex) => {
    if (activeButton !== buttonIndex) {
      setSavedPlan(null); 
    }
    setActiveButton(buttonIndex); 
    setError('');
  };
  
  const handleContinue = () => {
    if (activeButton) {
      setSavedPlan(activeButton); 
      setError(''); 
      console.log("Plan saved:", activeButton); 
    } else {
      setError('Please select a plan before continuing.'); 
    }
  };

  const handleOrderAndPay = () => {
    if (!savedPlan) {
      setError('Please confirm your plan before placing the order.'); 
      return;
    }

        console.log("Selected Plan:", savedPlan);
        console.log("Email:", email);
        console.log("Country:", country);
        console.log("Card Number:", cardNumber.replace(/\s/g, ''));
        console.log("Expiry Date:", `${expiryMonth}/${expiryYear}`);
        console.log("Security Code (CVC):", securityCode);
      };

  const handleCardInput = (e) => {
    let value = e.target.value.replace(/\D/g, '');
    value = value.replace(/(.{4})/g, '$1 ').trim();
    setCardNumber(value);
  };

  const handleYearChange = (e) => {
    let value = e.target.value.replace(/\D/g, '');
    if (value.length <= 4) {
      setExpiryYear(value);
    }
  };

  const handleSecurityCodeChange = (e) => {
    let value = e.target.value.replace(/\D/g, '');
    if (value.length <= 3) {
      setSecurityCode(value);
    }
  };

  const handleMonthChange = (e) => {
    setExpiryMonth(e.target.value);
  };
  return (
    <div className="payconteiners">
      <div className="payment-cont1">
        <div className="cont1-txt">Choose a payment plan: </div>
       
        <button className={`cont1-plan2 ${activeButton === 2 ? 'active' : ''}`} onClick={() => handleButtonClick(2)}>
          <div>Creation of 5 first portfolio, with download</div>
          <div className="cost">US$1</div>
          <div className="italic">A good plan for you if you are actively looking for a job right now</div>
        </button>
        <button className={`cont1-plan3 ${activeButton === 3 ? 'active' : ''}`} onClick={() => handleButtonClick(3)}>
          <div>Creation of 50 portfolio, with download</div>
          <div className="cost">US$5</div>
          <div className="italic">The right plan for you if you need to choose from several options or if you regularly change spheres of activity.</div>
        </button>
        <div className="cont1-txt-1">
          <TbLockCheck className="lockCheck" />
          <div>Secure transaction</div>
          <button onClick={handleContinue}>Continue</button> {/* Сохранение плана */}
        </div>
      </div>

      <div className="payment-cont2">
        <div className="cont2-txt1">Your email:</div>
        <div className="cont2-txt2">Payment information will be sent to this address</div>
        <div className="cont2-txt3">Your Country/Region:</div>
        <input type="email" id="email" name="email" className="pay-email" placeholder="example@example.com" value={email}
          onChange={(e) => setEmail(e.target.value)} required />
        <select id="country" name="country" className="country"value={country}
          onChange={(e) => setCountry(e.target.value)}>
          <option value="bl">Belarus</option>
          <option value="us">United States</option>
          <option value="ca">Canada</option>
          <option value="uk">United Kingdom</option>
          <option value="au">Australia</option>
          <option value="de">Germany</option>
          <option value="fr">France</option>
          <option value="jp">Japan</option>
          <option value="ru">Russia</option>
          <option value="pl">Poland</option>
        </select>
        <div className="card-container">
          <img src={Card} alt="Bank Card" className="bankcard" />
          <div className="form-fields">
            <div>
            <input
                type="text"
                id="cardNumber"
                className="card-input"
                value={cardNumber}
                onChange={handleCardInput}
                maxLength="19"
                placeholder="YYYY YYYY YYYY YYYY"
                required
              />
              {cardError && <div className="error-message">{cardError}</div>} {/* Вывод ошибки */}
            </div>
            <div>
              <select id="expiryMonth" value={expiryMonth} onChange={handleMonthChange} required className="select-month">
                <option value="01">January</option>
                <option value="02">February</option>
                <option value="03">March</option>
                <option value="04">April</option>
                <option value="05">May</option>
                <option value="06">June</option>
                <option value="07">July</option>
                <option value="08">August</option>
                <option value="09">September</option>
                <option value="10">October</option>
                <option value="11">November</option>
                <option value="12">December</option>
              </select>
              <input type="text" id="expiryYear" className="years" value={expiryYear} onChange={handleYearChange} maxLength="4" placeholder="YYYY" required />

              <div className="card-holder">Not required</div>
            </div>
            <input type="text" id="securityCode" className="cvc" value={securityCode} onChange={handleSecurityCodeChange} maxLength="3" placeholder="YYY" required />
          </div>
          <div className="pay-line"></div>
        </div>
        <div className="cont2-txt4">
          <div>By proceeding to purchase, you are entering into an agreement with Corp. Ilya&Masha under the</div>
          <div>terms specified below.</div>
          <div className="cont2-txt4-1">Personal data required in the order form is needed for entering into the agreement with us and</div>
          <div>delivery of the products or services (unless marked optional). We will process the personal data as</div>
          <div>described in our Privacy Policy. We may use Third-Party Services for the described purposes.</div>
          <div className="checkbox-container">
            <label>
              <span className="checkbox-label">I have read and agree</span>
              <input type="checkbox" className="checkbox" />
            </label>
          </div>
          <button onClick={handleOrderAndPay}>Order and Pay</button> {/* Отправка данных */}
        </div>

      </div>
    </div>


  )
}
 
export default Payment;