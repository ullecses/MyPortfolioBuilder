import { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

export const useGeneratorLogic = () => {
  const [activePlan, setActivePlan] = useState(null); 
  const location = useLocation(); 
  const navigate = useNavigate();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isWorkModalOpen, setIsWorkModalOpen] = useState(false);
  const [isLanguageModalOpen, setIsLanguageModalOpen] = useState(false);
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  useEffect(() => {
    const token = localStorage.getItem('token'); // Получаем токен из localStorage
    setIsAuthenticated(!!token); // Устанавливаем isAuthenticated в true, если токен существует
  }, []);
  const [educations, setEducations] = useState([]);
  const [newLanguages, setNewLanguages] = useState([
    { name: '', level: 1 }
  ]);
  const [newEducations, setNewEducations] = useState([
    { specialization: '', institution: '', city: '', startDate: '', endDate: '', educationInfo: '' }
  ]);

  const [jobs, setJobs] = useState([]);
  const [newJobs, setNewJobs] = useState([
    { position: '', company: '', city: '', startDate: '', endDate: '', jobsnInfo: '' }
  ]);

  useEffect(() => {
    if (location.hash) {
      const elementId = location.hash.substring(1);
      const element = document.getElementById(elementId);
      if (element) {
        element.scrollIntoView({ behavior: 'smooth' });
        if (elementId === 'gener-plan1') setActivePlan(1);
        else if (elementId === 'gener-plan2') setActivePlan(2);
        else if (elementId === 'gener-plan3') setActivePlan(3);
        else if (elementId === 'block-container') {
          setActivePlan(null);
        }
      }
    }
  }, [location]);

  const handlePlanClick = (planNumber) => {
    setActivePlan(planNumber);
  };

  const openModal = (event) => {
    event.preventDefault();
    setIsModalOpen(true);
  };

  const openWorkModal = (event) => {
    event.preventDefault();
    setIsWorkModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  const closeWorkModal = () => {
    setIsWorkModalOpen(false);
  };

  const addEducationField = () => {
    setNewEducations([...newEducations, { specialization: '', institution: '', city: '', startDate: '', endDate: '', educationInfo: '' }]);
  };

  const addWorkField = () => {
    setNewJobs([...newJobs, { position: '', company: '', city: '', startDate: '', endDate: '', jobsnInfo: '' }]);
  };

  const openLanguageModal = (event) => {
    event.preventDefault();
    setIsLanguageModalOpen(true);
  } 

  const closeLanguageModal = () => setIsLanguageModalOpen(false); // Закрыть окно языка

  const addLanguageField = () => { // Добавить новый язык
    setNewLanguages([...newLanguages, { name: '', level: 1 }]);
  };

  const deleteLanguageField = (index) => {
    setNewLanguages(newLanguages.filter((_, i) => i !== index));
  };

  const handleChange = (index, field, value, type = 'education') => {
    if (type === 'education') {
      const updatedEducations = [...newEducations];
      updatedEducations[index][field] = value;
      setNewEducations(updatedEducations);
    } else if (type === 'job') {
      const updatedJobs = [...newJobs];
      updatedJobs[index][field] = value;
      setNewJobs(updatedJobs);
    } else if (type === 'language') {
      const updatedLanguages = [...newLanguages];
      updatedLanguages[index][field] = value;
      setNewLanguages(updatedLanguages);
    }
  };

  const deleteEducationField = (index) => {
    const updatedEducations = newEducations.filter((_, i) => i !== index);
    setNewEducations(updatedEducations);
  };

  const deleteWorkField = (index) => {
    const updatedJobs = newJobs.filter((_, i) => i !== index);
    setNewJobs(updatedJobs);
  };

  const saveEducations = () => {
    setEducations([...educations, ...newEducations]);
    closeModal();
  };

  const saveJobs = () => {
    setJobs([...jobs, ...newJobs]);
    closeWorkModal();
  };

  const saveLanguages = () => {
    closeLanguageModal();
  };

  const [formData, setFormData] = useState({}); // состояние для хранения данных формы

  const handleSubmit = async (event) => {
    event.preventDefault(); // предотвращает перезагрузку страницы при отправке формы

    const form = event.target;
    const formDataObj = new FormData(form);

    // Собираем данные формы в объект
    const formValues = {};
    formDataObj.forEach((value, key) => {
      formValues[key] = value;
    });

    const formData = new FormData(event.target);

    // Добавляем данные из модальных окон (образование, языки, работа и т.д.)
    const educationData = educations;
    const jobData = jobs;
    const languageData = newLanguages;

    // Собираем все данные в один объект
    const fullFormData = {
      ...Object.fromEntries(formData),
      education: educationData,
      jobs: jobData,
      languages: languageData
    };

    console.log('Form Data:', fullFormData);

    setFormData(formValues);

    try {
      const response = await fetch('#', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json', 
        },
        body: JSON.stringify(fullFormData) 
      });

      if (!response.ok) { 
        throw new Error(`HTTP error! Status: ${response.status}`);
      }

      const responseData = await response.json(); 
      console.log('Response from server:', responseData); 

      if (activePlan === 2 || activePlan === 3) {
        navigate(`/payment?plan=${activePlan}`);
      } else {
        // План 1: просто сохраняем данные
        console.log('Plan 1 selected, no payment required.');
      }

    } catch (error) {
      console.error('Error sending data to server:', error); 
    }
  };
  return {
    activePlan,
    isModalOpen,
    isWorkModalOpen,
    newEducations,
    newJobs,
    newLanguages,
    openModal,
    closeModal,
    openWorkModal,
    closeWorkModal,
    handlePlanClick,
    addEducationField,
    addWorkField,
    handleChange,
    deleteEducationField,
    deleteWorkField,
    saveEducations,
    saveJobs,
    isLanguageModalOpen,
    openLanguageModal,
    closeLanguageModal,
    addLanguageField,
    saveLanguages,
    deleteLanguageField,
    handleSubmit,
    isAuthenticated,
  };
};
