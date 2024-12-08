// src/i18n.js
import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';

const resources = {
  en: {
    translation: {
      loginPage: {
        welcome: "Welcome to Generator",
        email: "Email",
        enterEmail: "Enter your email",
        password: "Password",
        enterPassword: "Enter your password",
        login: "Login",
        noAccount: "Don't have an account?",
        register: "Register",
        invalidEmailPassword: "Invalid email or password",
        somethingWrong: "Something went wrong. Please try again."
      },
      registerPage: {
        title: "Register",
        username: "Username",
        enterUsername: "Enter your username",
        confirmPassword: "Confirm Password",
        enterConfirmPassword: "Re-enter your password",
        createAccount: "Create Account",
        alreadyHaveAccount: "Already have an account?",
        login: "Login"
      },
      homePage: {
        createPortfolio: "Create your portfolio",
        inMinutes: "in 15 minutes",
        helpMessage: "We help everyone who wants to find a job without difficulties!",
        register: "Register",
        createSingleSystem: "Create your portfolio",
        createSingleSystem1:"in a single system",
        suitableFor: "Who is PortfolioBuilder suitable for?",
        studentNoJob: "A student without а",
        noJob:"job?",
        studentHelp: "Our software helps you create your first",
        stHelp2: "portfolio even without job experience.",
        changeProfession: "Want to change",
        chprof: "profession?", 
        changeHelp: "Our software provides portfolio creation",
        change1Help: "of a portfolio based on existing",
        change2Help: "experience",
        undecided: "Don’t know what",
        undecided1: "you want?",
        undecidedHelp: "Our software will help you decide on",
        undecidedHelp1: "a profession based on your ",
        undecidedHelp2: "preferences",
        howCreatePortfolio: "How you can create a portfolio in",
        howCreatePortfolio1: "our software",
        createPortfolioDesc: "Creating a portfolio in our software is quick and easy. First, choose one of our customized plans depending on your needs — whether you are looking for a free option or a premium plan with advanced features. After selecting a plan, you will be asked to enter your portfolio data, such as personal such as personal data, project descriptions, and images. If you have chosen a paid plan, you will need to complete the secure payment process before moving on. After confirming the payment, you will have full access to customize the design and layout of your portfolio. You can customize each element according to your style and professional needs. Finally, review your portfolio, make the latest changes, and you're ready to publish it and share it with potential clients or employers. It's so easy!",
        learnMore: "Learn More",
        howItWorks: "How it works?",
        howItWorksDesc: "Our application simplifies the process of creating a portfolio in just a few steps. First, you choose the plan that suits your needs, free or paid. Then enter data about your project and customize the layout according to your style. Finally, if you have chosen a paid plan, complete the payment and your portfolio is ready to be published worldwide!",
        weDoingGreat: "We doing our job",
        great: "Great",
        reachedHere: "We reached here with our hard work and dedication",
        usersCount: "Users",
        paymentsCount: "Payments",
        pricing: "PRICING",
        pricingPlans: "Our pricing plans",
        forIndividuals: "For individuals",
        basic: "Basic",
        basicDesc: "Creation of your first portfolio,",
        basicDesc1: "without download",
        whatsIncluded: "What’s included:",
        onePortfolio: "One portfolio generating",
        noDownload: "Downloading",
        getStarted: "Get started",
        forStartups: "For startups",
        pro: "Pro",
        proDesc: "Creation of 5 first portfolio, with",
        proDesc1: "download",
        fivePortfolios: "Five portfolio generating",
        forCompanies: "For big companies",
        enterprise: "Enterprise",
        enterpriseDesc: "Creation of 50 portfolios, with",
        enterpriseDesc1: "download",
        fiftyPortfolios: "Fifty portfolio generating"
      },
      header: {
        title: "Your Portfolio",
        home:"Home",
        generator:"Generator",
        faq:"FAQ",
        examples:"Examples",
        language: "Language",
        loginBtn:"Login",
        registerBtn:"Register",
        logOutBtn:"Log out"
      }
    }
  },
  ru: {
    translation: {
      loginPage: {
        welcome: "Добро пожаловать в Генератор",
        email: "Электронная почта",
        enterEmail: "Введите вашу почту",
        password: "Пароль",
        enterPassword: "Введите ваш пароль",
        login: "Войти",
        noAccount: "Нет аккаунта?",
        register: "Зарегистрироваться",
        invalidEmailPassword: "Неверная почта или пароль",
        somethingWrong: "Что-то пошло не так. Попробуйте снова."
      },
      registerPage: {
        title: "Регистрация",
        username: "Имя пользователя",
        enterUsername: "Введите ваше имя",
        confirmPassword: "Подтвердите пароль",
        enterConfirmPassword: "Повторите ваш пароль",
        createAccount: "Создать аккаунт",
        alreadyHaveAccount: "Уже есть аккаунт?",
        login: "Войти"
      },
      homePage: {
        createPortfolio: "Создайте свое портфолио",
        inMinutes: "за 15 минут",
        helpMessage: "Мы помогаем всем, кто хочет найти работу без трудностей!",
        register: "Регистрация",
        createSingleSystem: "Создайте свое портфолио",
        createSingleSystem1: "в этой системе",
        suitableFor: "Кому подходит PortfolioBuilder?",
        studentNoJob: "Студент без",
        noJob: "работы?",
        studentHelp: "Наше ПО помогает создать ваше первое",
        stHelp2: "портфолио даже без опыта работы.",
        changeProfession: "Хотите сменить",
        chprof: "профессию?",
        changeHelp: "Наше ПО позволяет создать портфолио",
        change1Help: "на основе имеющегося",
        change2Help: "опыта",
        undecided: "Не знаете, что",
        undecided1: "вам нужно?",
        undecidedHelp: "Наше ПО поможет вам определиться с",
        undecidedHelp1: "профессией на основе ваших",
        undecidedHelp2: "предпочтений",
        howCreatePortfolio: "Как создать портфолио в",
        howCreatePortfolio1: "нашем программном обеспечении",
        createPortfolioDesc: "Создать портфолио в нашем ПО просто и быстро. Сначала выберите один из наших планов в зависимости от ваших потребностей — бесплатный вариант или премиум-план с расширенными функциями. После выбора плана вас попросят ввести данные для портфолио, такие как личные данные, описания проектов и изображения. Если вы выбрали платный план, вам потребуется пройти безопасную процедуру оплаты. После подтверждения оплаты вы получите полный доступ к настройке дизайна и макета портфолио. Вы можете настроить каждый элемент в соответствии с вашим стилем и профессиональными потребностями. Наконец, просмотрите свое портфолио, внесите последние изменения, и оно готово для публикации и демонстрации потенциальным клиентам или работодателям. Это так просто!",
        learnMore: "Узнать больше",
        howItWorks: "Как это работает?",
        howItWorksDesc: "Наше приложение упрощает процесс создания портфолио до нескольких шагов. Сначала выберите подходящий план — бесплатный или платный. Затем введите данные о своем проекте и настройте макет по своему стилю. Наконец, если вы выбрали платный план, завершите оплату, и ваше портфолио будет готово для публикации в мире!",
        weDoingGreat: "Мы делаем свою работу",
        great: "Превосходно",
        reachedHere: "Мы достигли этого благодаря упорному труду и преданности делу",
        usersCount: "Пользователей",
        paymentsCount: "Платежей",
        pricing: "ЦЕНЫ",
        pricingPlans: "Наши тарифные планы",
        forIndividuals: "Индивидуальный",
        basic: "Базовый",
        basicDesc: "Создание вашего первого портфолио,",
        basicDesc1: "без возможности загрузки",
        whatsIncluded: "Что входит:",
        onePortfolio: "Создание одного портфолио",
        noDownload: "Загрузка",
        getStarted: "Начать",
        forStartups: "Для стартапов",
        pro: "Pro",
        proDesc: "Создание первых 5 портфолио, с",
        proDesc1: "возможностью загрузки",
        fivePortfolios: "Создание 5 портфолио",
        forCompanies: "Для крупных компаний",
        enterprise: "Энтерпрайз",
        enterpriseDesc: "Создание 50 портфолио, с",
        enterpriseDesc1: "возможностью загрузки",
        fiftyPortfolios: "Создание 50 портфолио"
    },
    header: {
      title: "Your Portfolio",
      home:"Главная",
      generator:"Генератор",
      faq:"Вопросы",
      examples:"Примеры",
      loginBtn:"Вход",
      registerBtn:"Регистрация",
      logOutBtn:"Выйти"
    }
    }
  }
};

i18n
  .use(initReactI18next)
  .init({
    resources,
    lng: "en",
    fallbackLng: "en",
    interpolation: {
      escapeValue: false,
    }
  });

export default i18n;
