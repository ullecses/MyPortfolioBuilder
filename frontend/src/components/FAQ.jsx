import '../styles/faq.css';
import React, { useState } from 'react';
import FAQ1 from '../img/FAQ1.svg';
import { IoIosArrowForward } from "react-icons/io";



function FAQ() {

   const [activeIndex, setActiveIndex] = useState(0);

   const toggleAnswer = (index) => {
     if (index === activeIndex) {
       setActiveIndex(null); 
     } else {
       setActiveIndex(index); 
     }
   };
  return (
    <div className="FAQ-page">
      <div className="faqBan1">
      <div className="faq-txt1">
          <div className="faq-txt1-1">Frequently </div>
          <div className="faq-txt1-2">asked <span style={{ color: '#4CAF4F' }}>questions!</span></div>
          <div className="faq-txt1-3">Find your question here!</div>
        </div>
        <img src={FAQ1} className="faq-phot1" alt="FAQ" />
      </div>
      <div className="faq-container">
        <div className="questions">
          <div className={`question ${activeIndex === 0 ? 'active' : ''}`} onClick={() => toggleAnswer(0)} >
            <div className="queCircle"></div>
            <h3>What is a Payment Gateway?</h3>
            <IoIosArrowForward  className="queArrow"/>
          </div>
          <div className={`question ${activeIndex === 1 ? 'active' : ''}`} onClick={() => toggleAnswer(1)} >
            <div className="queCircle"></div>
            <h3>Does Instapay provide international payments support?</h3>
            <IoIosArrowForward  className="queArrow"/>
          </div>
          <div className={`question ${activeIndex === 2 ? 'active' : ''}`}onClick={() => toggleAnswer(2)}>
            <div className="queCircle"></div>
            <h3>Do I need to pay to Instapay even when there is no transaction going on in my business?</h3>
            <IoIosArrowForward  className="queArrow"/>
          </div>

          <div className={`question ${activeIndex === 3 ? 'active' : ''}`} onClick={() => toggleAnswer(3)}>
            <div className="queCircle"></div>
            <h3>What platforms does Instapay payment gateway support?</h3>
            <IoIosArrowForward  className="queArrow"/>
          </div>

          <div className={`question ${activeIndex === 4 ? 'active' : ''}`} onClick={() => toggleAnswer(4)} >
            <div className="queCircle"></div>
            <h3>Is there any setup fee or annual maintainance fee that I need to pay regularly?</h3>
            <IoIosArrowForward  className="queArrow"/>
          </div>
        </div>
        <div className="answers">
          <div className={`answer ${activeIndex === 0 ? 'visible' : ''}`}>
            <h2>What is a Payment Gateway?</h2>
            <div className="ansN">
              A payment gateway is an ecommerce service that processes online
              payments for online as well as offline businesses. Payment gateways
              help accept payments by transferring key information from their
              merchant websites to issuing banks, card associations and online
              wallet players.
            </div>
            <div>
              Payment gateways play a vital role in the online transaction
              process, which is the realisation of value, and hence are seen as
              an important pillar of ecommerce.
            </div>
          </div>
          <div className={`answer ${activeIndex === 1 ? 'visible' : ''}`}>
          <h2>What is a Payment Gateway?</h2>
            <div className="ansN">Ответ на вопрос 2</div>
          </div>
          <div className={`answer ${activeIndex === 2 ? 'visible' : ''}`}>
          <h2>What is a Payment Gateway?</h2>
          <div className="ansN">Ответ на вопрос 3</div>
          </div>
          <div className={`answer ${activeIndex === 3 ? 'visible' : ''}`}>
          <h2>What is a Payment Gateway?</h2>
          <div className="ansN">Ответ на вопрос 4</div>
          </div>
          <div className={`answer ${activeIndex === 4 ? 'visible' : ''}`}>
          <h2>What is a Payment Gateway?</h2>
          <div className="ansN">Ответ на вопрос 5</div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default FAQ;