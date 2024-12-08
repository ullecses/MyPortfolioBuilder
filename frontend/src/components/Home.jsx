import '../styles/home.css';
import { Link } from 'react-router-dom'; 
import banner1Image1 from '../img/Illustration.png';
import banner2Image1 from '../img/Icon1.png';
import banner2Image2 from '../img/Icon2.png';
import banner2Image3 from '../img/Icon3.png';
import banner3Image1 from '../img/pana.png';
import banner4Image1 from '../img/rafiki.png';
import banner6Image1 from '../img/Group39506.svg';
import { GoPeople } from "react-icons/go";
import { CiCreditCard1 } from "react-icons/ci";
import React, { useState } from 'react';
import { FaCheckCircle } from "react-icons/fa";
import { IoMdCloseCircle } from "react-icons/io";
import { useTranslation } from 'react-i18next';



function Home() {
  const [activePlan, setActivePlan] = useState(null);

  const { t } = useTranslation();
  const handlePlanClick = (planNumber) => {
    setActivePlan(planNumber); 
  };

  return (
    <div className="Home">
      <div className="banner1-home">
        <div className="text-ban1">
          <div>{t('homePage.createPortfolio')}</div>
          <div className="min15">{t('homePage.inMinutes')}</div>
          <div className="we-help">{t('homePage.helpMessage')}</div>
          <Link to="/register" className='ban1-btn-reg'>{t('homePage.register')}</Link>
        </div>
        <img src={banner1Image1} alt="Banner1-home" className="banner-img1-home" />
      </div>
      <div className="banner2-home">
        <div className='text-ban2'>
          <div>{t('homePage.createSingleSystem')}</div>
          <div>{t('homePage.createSingleSystem1')}</div>
          <div className='who-is'>{t('homePage.suitableFor')}</div>
        </div>
        <div className="icon-text-ban2">
          <div className="icon-text-ban2-1">
            <img src={banner2Image1} className="banner2-img1-home" />
            <div className='text-ban2-1'>
              <div>{t('homePage.studentNoJob')}</div>
              <div>{t('homePage.noJob')}</div>
            </div>
            <div className='text-ban2-1-1'>
              <div >{t('homePage.studentHelp')}</div>
              <div >{t('homePage.stHelp2')}</div>
            </div>
          </div>
          <div className="icon-text-ban2-2">
            <img src={banner2Image2} className="banner2-img2-home" />
            <div className='text-ban2-2'>
              <div>{t('homePage.changeProfession')}</div>
              <div>{t('homePage.chprof')}</div>
            </div>
            <div className='text-ban2-2-2'>
              <div >{t('homePage.changeHelp')}</div>
              <div >{t('homePage.change1Help')}</div>
              <div>{t('homePage.change2Help')}</div>
            </div>

          </div>
          <div className="icon-text-ban2-3">
            <img src={banner2Image3} className="banner2-img3-home" />
            <div className='text-ban2-3'>
              <div>{t('homePage.undecided')}</div>
              <div>{t('homePage.undecided1')}</div>
            </div>
            <div className='text-ban2-3-3'>
              <div >{t('homePage.undecidedHelp')}</div>
              <div >{t('homePage.undecidedHelp1')} </div>
              <div>{t('homePage.undecidedHelp2')}</div>
            </div>
          </div>

        </div>
      </div>
      <div className="banner3-home">
        <img src={banner3Image1} />
        <div className='text-banner3'>
          <div className='text-ban3-1'>
            <div>{t('homePage.howCreatePortfolio')}</div>
            <div>{t('homePage.howCreatePortfolio1')}</div>
          </div>

          <div className='text-6line'>
          {t('homePage.createPortfolioDesc')}</div>
          <Link to="/generator#block-container" className="learnMoreBtn">{t('homePage.learnMore')}</Link>

        </div>
      </div>
      <div className="banner4-home">
        <img src={banner4Image1} />
        <div className='text-banner4'>
          <div className='text-ban4-1'>
          {t('homePage.howItWorks')} </div>

          <div className='text-4line'>
          {t('homePage.howItWorksDesc')} </div>
          <Link to="/generator#block-container" className="learnMoreBtn">{t('homePage.learnMore')}</Link>

        </div>
      </div>
      <div className="banner5-home">
        <div className='text-banner5'>
          <div>{t('homePage.weDoingGreat')}</div>
          <div className='great'>{t('homePage.great')}</div>
          <div className='we'>{t('homePage.reachedHere')}</div>
        </div>
        <div className='icon-text-ban5'>
          <div className='icon-text-ban5-1'>
            <GoPeople className="banner5-home-icons" />
            <div className='icon-text-ban5-1-1'>
              <p>2,245,341</p>
              <p className='p2'>{t('homePage.usersCount')}</p>
            </div>
          </div>
          <div className='icon-text-ban5-2'>
            <CiCreditCard1 className="banner5-home-icons" />
            <div className='icon-text-ban5-1-1'>
              <p>1,926,436</p>
              <p className='p2'>{t('homePage.paymentsCount')}</p>
            </div>
          </div>
        </div>

      </div>
      <div className="banner6-home">
        <div className="ban6-home-name">
          <div className="ban6-home-name1">{t('homePage.pricing')}</div>
          <div className="ban6-home-name2">{t('homePage.pricingPlans')}</div>
        </div>
        <button
          className={`plan plan1 ${activePlan === 1 ? 'active' : ''}`}
          onClick={() => handlePlanClick(1)}
        >
          <div className="circle-rec">
            <div className="half-circle1"></div>
            <div className="half-circle2"></div>
          </div>
          <div className="text-plan1-1">
            <div className="text-plan1-1-1">{t('homePage.forIndividuals')}</div>
            <div className="text-plan1-1-2">{t('homePage.basic')}</div>
          </div>
          <div className="text-plan1-2">
            <div>{t('homePage.basicDesc')}</div>
            <div>{t('homePage.basicDesc1')}</div>
          </div>
          <div className="text-plan1-3">
            <div className="text-plan1-3-1">$0</div>
            <div className="text-plan1-3-2">{t('homePage.whatsIncluded')}</div>
            <div className="check">
              <FaCheckCircle className="eleps-chek" />
              <div className="eleps-chek-text">{t('homePage.onePortfolio')}</div>
            </div>

            <div className="crosss">
              <IoMdCloseCircle className="eleps-cross" />
              <div className="cross-text-dov">{t('homePage.noDownload')}</div>
            </div>
          </div>
          <Link to="/generator#gener-plan1" className={`inner-btn ${activePlan === 1 ? 'active-btn' : ''}`}>{t('homePage.getStarted')}</Link>
        </button>

        <button
          className={`plan plan2 ${activePlan === 2 ? 'active' : ''}`}
          onClick={() => handlePlanClick(2)}
        >
          <div className="rectangle">
            <div className="rectang1"></div>
            <div className="rectang-container">
              <div className="rectang2"></div>
              <div className="rectang3"></div>
            </div>
          </div>
          <div className="text-plan2-1">
            <div className="text-plan2-1-1">{t('homePage.forStartups')}</div>
            <div className="text-plan2-1-2">{t('homePage.pro')}</div>
          </div>
          <div className="text-plan2-2">
            <div>{t('homePage.proDesc')}</div>
            <div>{t('homePage.proDesc1')}</div>
          </div>
          <div className="text-plan2-3">
            <div className="text-plan2-3-1">$1</div>
            <div className="text-plan2-3-2">{t('homePage.whatsIncluded')}</div>
            <div className="check">
              <FaCheckCircle className="eleps-chek" />
              <div className="eleps-chek-text">{t('homePage.fivePortfolios')}</div>
            </div>

            <div className="check2">
              <FaCheckCircle className="eleps-chek" />
              <div className="eleps-chek-text-2">{t('homePage.noDownload')}</div>
            </div>


          </div>
          <Link to="/generator#gener-plan2" className={`inner-btn ${activePlan === 2 ? 'active-btn' : ''}`}>{t('homePage.getStarted')}</Link>
        </button>

        <button
          className={`plan plan3 ${activePlan === 3 ? 'active' : ''}`}
          onClick={() => handlePlanClick(3)}
        >
          <div className="rectangle-pl3">
            <img src={banner6Image1} className="banner6-img1-home" />
          </div>
          <div className="text-plan3-1">
            <div className="text-plan3-1-1">{t('homePage.forCompanies')}</div>
            <div className="text-plan3-1-2">{t('homePage.enterprise')}</div>
          </div>
          <div className="text-plan3-2">
            <div>{t('homePage.enterpriseDesc')}</div>
            <div>{t('homePage.enterpriseDesc1')}</div>
          </div>
          <div className="text-plan3-3">
            <div className="text-plan3-3-1">$5</div>
            <div className="text-plan3-3-2">{t('homePage.whatsIncluded')}</div>
            <div className="check">
              <FaCheckCircle className="eleps-chek" />
              <div className="eleps-chek-text">{t('homePage.fiftyPortfolios')}</div>
            </div>

            <div className="check2">
              <FaCheckCircle className="eleps-chek" />
              <div className="eleps-chek-text-2">{t('homePage.noDownload')}</div>
            </div>


          </div>
          <Link to="/generator#gener-plan3" className={`inner-btn ${activePlan === 3 ? 'active-btn' : ''}`}>{t('homePage.getStarted')}</Link>
        </button>
      </div>
    </div>
  )
}

export default Home;