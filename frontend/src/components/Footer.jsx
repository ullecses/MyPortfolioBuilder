import React, { useState } from "react";
import { useLocation } from 'react-router-dom'; 
import '../styles/footer.css';
import { FaDribbble } from "react-icons/fa6";
import { IoLogoInstagram } from "react-icons/io5";
import { FaTwitter } from "react-icons/fa";
import { FaYoutube } from "react-icons/fa";
import { FiSend } from "react-icons/fi";
import Footerlogo from '../img/logo.svg';

function Footer() {
  const location = useLocation(); 
  const [email, setEmail] = useState("");

  const hideFooter = location.pathname === '/login' || location.pathname === '/register';

  // Если находимся на страницах логина или регистрации, не рендерим футер
  if (hideFooter) {
    return null; 
  }

  const handleSendEmail = (e) => {
    e.preventDefault();
    console.log("Email sent:", email);};

  return (
    <div className="footer">
      <div className="name_logo">
        <div className="footerlogo">
        <div style={{ display: 'flex', alignItems: 'center' }}>
  <img src={Footerlogo} className="footlogo" alt="Logo" style={{ marginRight: '10px' }} />
  <span>Your Portfolio</span>
</div>
        </div>
        <div className="name_logo-1">
          <p>Copyright © 2024 Nexcent ltd.</p>
          <p className="f-p1">All rights reserved</p>
        </div>
        <div className="icons-footer">
          <div className="icons-footer-1">
            <a href="https://www.instagram.com/" target="_blank" rel="noopener noreferrer">
              <IoLogoInstagram className="insta" />
            </a>
          </div>
          <div className="icons-footer-2">
            <a href="https://dribbble.com/" target="_blank" rel="noopener noreferrer">
              <FaDribbble className="dribble" />
            </a>
          </div>
          <div className="icons-footer-3">
            <a href="https://www.twitter.com/" target="_blank" rel="noopener noreferrer">
              <FaTwitter className="twitter" />
            </a>
          </div>
          <div className="icons-footer-4">
            <a href="https://www.youtube.com/" target="_blank" rel="noopener noreferrer">
              <FaYoutube className="youtube" />
            </a>
          </div>
        </div>

      </div>

      <div className="footer-block-tekst">
        <div className="footer-block-tekst-1">
          <div>Company</div>
          <a href="#">About us</a>
          <a href="#">Blog</a>
          <a href="#">Contact us</a>
          <a href="#">Pricing</a>
          <a href="#">Testimonials</a>
        </div>
        <div className="footer-block-tekst-2">
          <div>Support</div>
          <a href="#">Help center</a>
          <a href="#">Terms of service</a>
          <a href="#">Legal</a>
          <a href="#">Privacy policy</a>
          <a href="#">Status</a>
        </div>
        <div className="footer-block-tekst-3">
  <div className="footer-block-tekst-3-1">Stay up to date</div>
  <div className="email-input-wrapper">
    <input
      type="email"
      className="fotemail"
      placeholder="Enter your email"
      value={email}
      onChange={(e) => setEmail(e.target.value)}
      required
    />
    <button type="submittt" className="send-button">
      <FiSend className="send-icon" />
    </button>
  </div>
</div>
</div>
</div>
  );
}

export default Footer;
