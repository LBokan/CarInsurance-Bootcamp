import { reactive } from 'vue';
import { defineStore } from 'pinia';

type TypeArgsForFunc = any[];
type TypeOnConfirmActionFunc = (...args: TypeArgsForFunc) => void;

interface IConfirmationState {
  isOpen: boolean,
  title: string,
  content: string,
  onConfirmAction: TypeOnConfirmActionFunc,
  args: TypeArgsForFunc
};

interface ISetConfirmationDataAndShowArgs {
  title: string, 
  content?: string,
  onConfirmAction: TypeOnConfirmActionFunc,
  args?: TypeArgsForFunc
}

export const useConfirmationStore = defineStore('confirmation', () => {
  const confirmationState: IConfirmationState = reactive({
    isOpen: false,
    title: '',
    content: '',
    onConfirmAction: () => {},
    args: []
  });

  function setConfirmationDataAndShow({
    title, 
    content = '',
    onConfirmAction,
    args = []
  }: ISetConfirmationDataAndShowArgs) {
    confirmationState.title = title;
    confirmationState.content = content;
    confirmationState.onConfirmAction = onConfirmAction;
    confirmationState.args = args;

    confirmationState.isOpen = true;
  }

  function onYesBtnClick() {
    confirmationState.onConfirmAction(...confirmationState.args);

    closeAndResetOnNoBtnClick();
  }

  function closeAndResetOnNoBtnClick() {
    confirmationState.isOpen = false;
    confirmationState.title = '';
    confirmationState.content = '';
    confirmationState.onConfirmAction = () => {};
    confirmationState.args = [];
  }

  return { 
    confirmationState, 
    setConfirmationDataAndShow,
    onYesBtnClick,
    closeAndResetOnNoBtnClick 
  }
})
