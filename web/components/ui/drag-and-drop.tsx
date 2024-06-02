/* eslint-disable @typescript-eslint/no-explicit-any */
'use client';

import { displayAcceptedTypes } from '@/lib/utils';

import clsx from 'clsx';
import { UploadIcon, XIcon } from 'lucide-react';
import Image from 'next/image';
import { useState, type DragEvent } from 'react';
import type {
  FieldError,
  FieldValue,
  FieldValues,
  UseFormReturn,
} from 'react-hook-form';

import { FormControl, FormItem, FormLabel, FormMessage } from './form';
import { Input } from './input';

export interface DragAndDropProps {
  error?: FieldError;
  form: UseFormReturn<any>;
  fieldValue: FieldValue<FieldValues>;
  onUpload?: (files: FileList) => void;
  acceptedTypes: string[];
  maxSize: number;
}

export default function DragAndDrop({
  error,
  form,
  fieldValue,
  acceptedTypes,
  onUpload,
  maxSize,
}: DragAndDropProps) {
  const [file, setFile] = useState<string>();

  function handleDragOver(event: DragEvent<HTMLLabelElement>) {
    event.preventDefault();
    event.stopPropagation();
  }

  function readFile(file: File) {
    const reader = new FileReader();
    reader.readAsDataURL(file);

    reader.onload = () => {
      const dataUrl = reader.result;

      if (dataUrl) {
        setFile(dataUrl.toString());
      }
    };
  }

  function handleDrop(event: DragEvent<HTMLLabelElement>) {
    event.preventDefault();
    event.stopPropagation();

    const { files } = event.dataTransfer;

    if (files && files.length) {
      const file = files.item(0);

      if (file) readFile(file);
    }

    if (onUpload) onUpload(files);
  }

  const register = form.register(fieldValue);

  register.onChange = async (event) => {
    const files = event.target.files;

    if (files && files.length) {
      const file = files.item(0);

      if (file) readFile(file);
    }

    if (onUpload) onUpload(files);
  };

  function clear() {
    form.setValue(fieldValue, null);
    setFile(undefined);
  }

  return (
    <FormItem>
      <div className='flex w-full items-center justify-center'>
        <FormLabel
          htmlFor='dropzone-file'
          className={clsx(
            'dark:hover:bg-bray-800 z-0 flex h-64 w-full cursor-pointer flex-col items-center justify-center rounded-lg border-2 border-dashed bg-gray-50 hover:bg-gray-100 dark:bg-gray-700 dark:hover:bg-gray-600',
            {
              'border-input': !error,
              'border-destructive': !!error,
            }
          )}
          onDragOver={handleDragOver}
          onDrop={handleDrop}
        >
          <div
            className={clsx('flex-col items-center justify-center pb-6 pt-5', {
              flex: !file,
              hidden: !!file,
            })}
          >
            <UploadIcon className='mb-4 h-8 w-8 text-primary dark:text-primary-foreground' />
            <p className='mb-2 text-sm text-gray-500 dark:text-gray-400'>
              <span className='font-semibold text-primary dark:text-primary-foreground'>
                Clique para carregar
              </span>{' '}
              ou arraste e solte
            </p>
            <p className='text-xs text-gray-500 dark:text-gray-400'>
              {displayAcceptedTypes(acceptedTypes)} (MAX.{' '}
              {maxSize / (1024 * 1024)}MB)
            </p>
          </div>

          <div
            className={clsx(
              'relative z-10 h-64 w-64 items-center justify-center p-4',
              {
                flex: !!file,
                hidden: !file,
              }
            )}
          >
            {file && (
              <>
                <div
                  onClick={clear}
                  className='absolute right-0 top-5 flex cursor-pointer items-center justify-center rounded-full bg-primary p-1'
                >
                  <XIcon className='h-4 w-4 text-primary-foreground' />
                </div>
                <Image src={file} alt='Image' height={256} width={256} />
              </>
            )}
          </div>

          <FormControl>
            <Input
              id='dropzone-file'
              type='file'
              accept={acceptedTypes.join(', ')}
              className='hidden'
              multiple={false}
              {...register}
            />
          </FormControl>
        </FormLabel>
      </div>
      <FormMessage />
    </FormItem>
  );
}
