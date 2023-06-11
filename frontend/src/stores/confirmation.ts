import { reactive } from 'vue';
import { defineStore } from 'pinia';

type TypeArgsForFunc = any[];
type TypeActionOnYesBtnChosenFunc = (...args: TypeArgsForFunc) => void;

interface IConfirmationState {
  isOpen: boolean,
  title: string,
  content: string,
  actionOnYesBtnChosen: TypeActionOnYesBtnChosenFunc,
  argsForFunc: TypeArgsForFunc
};

export const useConfirmationStore = defineStore('confirmation', () => {
  const confirmationState: IConfirmationState = reactive({
    isOpen: false,
    title: '',
    content: '',
    actionOnYesBtnChosen: () => {},
    argsForFunc: []
  });

  function setConfirmationDataAndShow( 
    title: string, 
    content: string = '',
    actionOnYesBtnChosen: TypeActionOnYesBtnChosenFunc,
    argsForFunc: TypeArgsForFunc = []
  ) {
    confirmationState.title = title;
    confirmationState.content = content;
    confirmationState.actionOnYesBtnChosen = actionOnYesBtnChosen;
    confirmationState.argsForFunc = argsForFunc;

    confirmationState.isOpen = true;
  }

  function onYesBtnClick() {
    confirmationState.actionOnYesBtnChosen(...confirmationState.argsForFunc);

    closeAndResetOnNoBtnClick();
  }

  function closeAndResetOnNoBtnClick() {
    confirmationState.isOpen = false;
    confirmationState.title = '';
    confirmationState.content = '';
  }

  return { 
    confirmationState, 
    setConfirmationDataAndShow,
    onYesBtnClick,
    closeAndResetOnNoBtnClick 
  }
})
