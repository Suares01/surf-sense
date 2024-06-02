'use client';

import H4 from '@/components/typography/h4';
import Small from '@/components/typography/small';
import { Button } from '@/components/ui/button';
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from '@/components/ui/dialog';
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from '@/components/ui/form';
import { Input } from '@/components/ui/input';
import { ScrollArea } from '@/components/ui/scroll-area';
import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectLabel,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select';
import { Separator } from '@/components/ui/separator';

import { handleCriterionTypeLabel, handleRatingTypeLabel } from '@/lib/utils';

import { zodResolver } from '@hookform/resolvers/zod';
import { PlusIcon } from 'lucide-react';
import { useFieldArray, useForm } from 'react-hook-form';
import { z } from 'zod';

export enum CriterionType {
  WAVE_HEIGHT = 'WAVE_HEIGHT',
  WAVE_DIRECTION = 'WAVE_DIRECTION',
  WAVE_PERIOD = 'WAVE_PERIOD',
  SWELL_HEIGHT = 'SWELL_HEIGHT',
  SWELL_DIRECTION = 'SWELL_DIRECTION',
  SWELL_PERIOD = 'SWELL_PERIOD',
  WIND_DIRECTION = 'WIND_DIRECTION',
}

export enum RatingType {
  SURFER = 'SURFER',
  BEACHGOER = 'BEACHGOER',
}

const createRatingFormScheme = z.object({
  name: z.string().min(1),
  type: z.nativeEnum(RatingType),
  criteria: z.array(
    z.object({
      name: z.string().min(1),
      type: z.nativeEnum(CriterionType),
      weight: z.number().min(0).max(1),
    })
  ),
});

type FormFields = z.infer<typeof createRatingFormScheme>;

const MAX_CRITERIA = 7 as const;

export default function RatingsPage() {
  const form = useForm<FormFields>({
    defaultValues: {
      name: '',
    },
    resolver: zodResolver(createRatingFormScheme),
  });

  const { fields, append, remove, update } = useFieldArray({
    control: form.control,
    name: 'criteria',
  });

  function handleCreateRating(data: FormFields) {
    console.log(data);
  }

  function appendCriterion() {
    append({ name: '', weight: 0, type: CriterionType.SWELL_DIRECTION });
  }

  return (
    <Dialog>
      <DialogTrigger asChild>
        <Button variant='outline'>Criar</Button>
      </DialogTrigger>

      <DialogContent>
        <DialogHeader>
          <DialogTitle>Criar Avaliação</DialogTitle>
          <DialogDescription>
            Consulte guia de avaliações em caso de dúvidas.
          </DialogDescription>
        </DialogHeader>

        <Form {...form}>
          <form
            className='p-2'
            onSubmit={form.handleSubmit(handleCreateRating)}
            noValidate
          >
            <ScrollArea>
              <div className='h-96 space-y-4 p-4'>
                <FormField
                  control={form.control}
                  name='name'
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Nome</FormLabel>
                      <FormControl>
                        <Input
                          type='text'
                          placeholder='Nome da avaliação'
                          {...field}
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />

                <FormField
                  control={form.control}
                  name='type'
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Tipo</FormLabel>
                      <FormControl>
                        <Select
                          onValueChange={field.onChange}
                          defaultValue={field.value}
                        >
                          <SelectTrigger>
                            <SelectValue placeholder='Selecione o tipo' />
                          </SelectTrigger>

                          <SelectContent>
                            <SelectGroup>
                              <SelectLabel>Tipos</SelectLabel>
                              {Object.entries(RatingType).map(([, value]) => (
                                <SelectItem key={value} value={value}>
                                  {handleRatingTypeLabel(value)}
                                </SelectItem>
                              ))}
                            </SelectGroup>
                          </SelectContent>
                        </Select>
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />

                <fieldset>
                  <div className='flex items-center justify-between'>
                    <legend>
                      <H4>Critérios</H4>
                    </legend>
                    <Button
                      type='button'
                      onClick={appendCriterion}
                      variant='outline'
                      disabled={fields.length === MAX_CRITERIA}
                    >
                      <PlusIcon className='mr-2 h-4 w-4' /> Adicionar
                    </Button>
                  </div>

                  <div className='mt-2 flex flex-col gap-2'>
                    {fields.map((arrayField, index) => (
                      <div key={arrayField.id} className='flex flex-col gap-1'>
                        <div className='flex items-center'>
                          <Small>
                            {arrayField.name || `Critério ${index + 1}`}
                          </Small>
                          <Button
                            variant='ghost'
                            type='button'
                            className='underline'
                            onClick={() => remove(index)}
                          >
                            remover
                          </Button>
                        </div>

                        <FormField
                          control={form.control}
                          name={`criteria.${index}.name`}
                          render={({ field }) => (
                            <FormItem>
                              <FormLabel>Nome</FormLabel>
                              <FormControl>
                                <Input
                                  type='text'
                                  placeholder='Nome do critério'
                                  {...field}
                                />
                              </FormControl>
                              <FormMessage />
                            </FormItem>
                          )}
                        />
                        <FormField
                          control={form.control}
                          name={`criteria.${index}.type`}
                          render={({ field }) => (
                            <FormItem>
                              <FormLabel>Tipo</FormLabel>
                              <FormControl>
                                <Select
                                  onValueChange={field.onChange}
                                  defaultValue={field.value}
                                >
                                  <SelectTrigger>
                                    <SelectValue placeholder='Selecione o tipo' />
                                  </SelectTrigger>

                                  <SelectContent>
                                    <SelectGroup>
                                      <SelectLabel>Tipos</SelectLabel>
                                      {Object.entries(CriterionType).map(
                                        ([, value]) => (
                                          <SelectItem key={value} value={value}>
                                            {handleCriterionTypeLabel(value)}
                                          </SelectItem>
                                        )
                                      )}
                                    </SelectGroup>
                                  </SelectContent>
                                </Select>
                              </FormControl>
                              <FormMessage />
                            </FormItem>
                          )}
                        />
                        <FormItem>
                          <FormLabel>Peso</FormLabel>
                          <FormControl>
                            <Input
                              type='number'
                              max={1}
                              min={0}
                              placeholder='0.5'
                              {...form.register(`criteria.${index}.weight`, {
                                valueAsNumber: true,
                              })}
                            />
                          </FormControl>
                          <FormMessage />
                        </FormItem>
                        {fields.length - 1 !== index && (
                          <Separator className='mt-2' />
                        )}
                      </div>
                    ))}
                  </div>
                </fieldset>
              </div>
            </ScrollArea>

            <DialogFooter className='mt-2'>
              <Button type='submit'>Criar</Button>
            </DialogFooter>
          </form>
        </Form>
      </DialogContent>
    </Dialog>
  );
}
