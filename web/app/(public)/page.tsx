import Image from 'next/image';

export default function Home() {
  return (
    <>
      <section className='body-font text-gray-600'>
        <div className='container mx-auto flex flex-col items-center px-5 py-24 md:flex-row'>
          <div className='mb-10 w-5/6 md:mb-0 md:w-1/2 lg:w-full lg:max-w-lg'>
            <Image
              height={720}
              width={600}
              className='rounded object-cover object-center'
              alt='hero'
              src='https://dummyimage.com/720x600'
            />
          </div>
          <div className='flex flex-col items-center text-center md:w-1/2 md:items-start md:pl-16 md:text-left lg:flex-grow lg:pl-24'>
            <h1 className='title-font mb-4 text-3xl font-medium text-gray-900 sm:text-4xl'>
              Before they sold out
              <br className='hidden lg:inline-block' />
              readymade gluten
            </h1>
            <p className='mb-8 leading-relaxed'>
              Copper mug try-hard pitchfork pour-over freegan heirloom neutra
              air plant cold-pressed tacos poke beard tote bag. Heirloom echo
              park mlkshk tote bag selvage hot chicken authentic tumeric
              truffaut hexagon try-hard chambray.
            </p>
            <div className='flex justify-center'>
              <button className='inline-flex rounded border-0 bg-blue-500 px-6 py-2 text-lg text-white hover:bg-blue-600 focus:outline-none'>
                Button
              </button>
              <button className='ml-4 inline-flex rounded border-0 bg-gray-100 px-6 py-2 text-lg text-gray-700 hover:bg-gray-200 focus:outline-none'>
                Button
              </button>
            </div>
          </div>
        </div>
      </section>

      <section id='sobre' className='body-font text-gray-600'>
        <div className='container mx-auto flex flex-col px-5 py-24'>
          <div className='mx-auto lg:w-4/6'>
            <div className='h-64 overflow-hidden rounded-lg'>
              <Image
                width={1200}
                height={500}
                alt='content'
                className='h-full w-full object-cover object-center'
                src='https://dummyimage.com/1200x500'
              />
            </div>
            <div className='mt-10 flex flex-col sm:flex-row'>
              <div className='text-center sm:w-1/3 sm:py-8 sm:pr-8'>
                <div className='inline-flex h-20 w-20 items-center justify-center rounded-full bg-gray-200 text-gray-400'>
                  <svg
                    fill='none'
                    stroke='currentColor'
                    strokeLinecap='round'
                    strokeLinejoin='round'
                    strokeWidth={2}
                    className='h-10 w-10'
                    viewBox='0 0 24 24'
                  >
                    <path d='M20 21v-2a4 4 0 00-4-4H8a4 4 0 00-4 4v2' />
                    <circle cx={12} cy={7} r={4} />
                  </svg>
                </div>
                <div className='flex flex-col items-center justify-center text-center'>
                  <h2 className='title-font mt-4 text-lg font-medium text-gray-900'>
                    Phoebe Caulfield
                  </h2>
                  <div className='mb-4 mt-2 h-1 w-12 rounded bg-indigo-500'></div>
                  <p className='text-base'>
                    Raclette knausgaard hella meggs normcore williamsburg enamel
                    pin sartorial venmo tbh hot chicken gentrify portland.
                  </p>
                </div>
              </div>
              <div className='mt-4 border-t border-gray-200 pt-4 text-center sm:mt-0 sm:w-2/3 sm:border-l sm:border-t-0 sm:py-8 sm:pl-8 sm:text-left'>
                <p className='mb-4 text-lg leading-relaxed'>
                  Meggings portland fingerstache lyft, post-ironic fixie man bun
                  banh mi umami everyday carry hexagon locavore direct trade art
                  party. Locavore small batch listicle gastropub farm-to-table
                  lumbersexual salvia messenger bag. Coloring book flannel
                  truffaut craft beer drinking vinegar sartorial, disrupt
                  fashion axe normcore meh butcher. Portland 90&apos;s scenester
                  vexillologist forage post-ironic asymmetrical, chartreuse
                  disrupt butcher paleo intelligentsia pabst before they sold
                  out four loko. 3 wolf moon brooklyn.
                </p>
                <a className='inline-flex items-center text-indigo-500'>
                  Learn More
                  <svg
                    fill='none'
                    stroke='currentColor'
                    strokeLinecap='round'
                    strokeLinejoin='round'
                    strokeWidth={2}
                    className='ml-2 h-4 w-4'
                    viewBox='0 0 24 24'
                  >
                    <path d='M5 12h14M12 5l7 7-7 7' />
                  </svg>
                </a>
              </div>
            </div>
          </div>
        </div>
      </section>

      <section id='funcionalidades' className='body-font text-gray-600'>
        <div className='container mx-auto px-5 py-24'>
          <div className='mb-20 text-center'>
            <h1 className='title-font mb-4 text-2xl font-medium text-gray-900 sm:text-3xl'>
              Raw Denim Heirloom Man Braid
            </h1>
            <p className='text-gray-500s mx-auto text-base leading-relaxed lg:w-3/4 xl:w-2/4'>
              Blue bottle crucifix vinyl post-ironic four dollar toast vegan
              taxidermy. Gastropub indxgo juice poutine, ramps microdosing banh
              mi pug.
            </p>
            <div className='mt-6 flex justify-center'>
              <div className='inline-flex h-1 w-16 rounded-full bg-indigo-500'></div>
            </div>
          </div>
          <div className='-mx-4 -mb-10 -mt-4 flex flex-wrap space-y-6 sm:-m-4 md:space-y-0'>
            <div className='flex flex-col items-center p-4 text-center md:w-1/3'>
              <div className='mb-5 inline-flex h-20 w-20 flex-shrink-0 items-center justify-center rounded-full bg-indigo-100 text-indigo-500'>
                <svg
                  fill='none'
                  stroke='currentColor'
                  strokeLinecap='round'
                  strokeLinejoin='round'
                  strokeWidth={2}
                  className='h-10 w-10'
                  viewBox='0 0 24 24'
                >
                  <path d='M22 12h-4l-3 9L9 3l-3 9H2' />
                </svg>
              </div>
              <div className='flex-grow'>
                <h2 className='title-font mb-3 text-lg font-medium text-gray-900'>
                  Shooting Stars
                </h2>
                <p className='text-base leading-relaxed'>
                  Blue bottle crucifix vinyl post-ironic four dollar toast vegan
                  taxidermy. Gastropub indxgo juice poutine, ramps microdosing
                  banh mi pug VHS try-hard.
                </p>
                <a className='mt-3 inline-flex items-center text-indigo-500'>
                  Learn More
                  <svg
                    fill='none'
                    stroke='currentColor'
                    strokeLinecap='round'
                    strokeLinejoin='round'
                    strokeWidth={2}
                    className='ml-2 h-4 w-4'
                    viewBox='0 0 24 24'
                  >
                    <path d='M5 12h14M12 5l7 7-7 7' />
                  </svg>
                </a>
              </div>
            </div>
            <div className='flex flex-col items-center p-4 text-center md:w-1/3'>
              <div className='mb-5 inline-flex h-20 w-20 flex-shrink-0 items-center justify-center rounded-full bg-indigo-100 text-indigo-500'>
                <svg
                  fill='none'
                  stroke='currentColor'
                  strokeLinecap='round'
                  strokeLinejoin='round'
                  strokeWidth={2}
                  className='h-10 w-10'
                  viewBox='0 0 24 24'
                >
                  <circle cx={6} cy={6} r={3} />
                  <circle cx={6} cy={18} r={3} />
                  <path d='M20 4L8.12 15.88M14.47 14.48L20 20M8.12 8.12L12 12' />
                </svg>
              </div>
              <div className='flex-grow'>
                <h2 className='title-font mb-3 text-lg font-medium text-gray-900'>
                  The Catalyzer
                </h2>
                <p className='text-base leading-relaxed'>
                  Blue bottle crucifix vinyl post-ironic four dollar toast vegan
                  taxidermy. Gastropub indxgo juice poutine, ramps microdosing
                  banh mi pug VHS try-hard.
                </p>
                <a className='mt-3 inline-flex items-center text-indigo-500'>
                  Learn More
                  <svg
                    fill='none'
                    stroke='currentColor'
                    strokeLinecap='round'
                    strokeLinejoin='round'
                    strokeWidth={2}
                    className='ml-2 h-4 w-4'
                    viewBox='0 0 24 24'
                  >
                    <path d='M5 12h14M12 5l7 7-7 7' />
                  </svg>
                </a>
              </div>
            </div>
            <div className='flex flex-col items-center p-4 text-center md:w-1/3'>
              <div className='mb-5 inline-flex h-20 w-20 flex-shrink-0 items-center justify-center rounded-full bg-indigo-100 text-indigo-500'>
                <svg
                  fill='none'
                  stroke='currentColor'
                  strokeLinecap='round'
                  strokeLinejoin='round'
                  strokeWidth={2}
                  className='h-10 w-10'
                  viewBox='0 0 24 24'
                >
                  <path d='M20 21v-2a4 4 0 00-4-4H8a4 4 0 00-4 4v2' />
                  <circle cx={12} cy={7} r={4} />
                </svg>
              </div>
              <div className='flex-grow'>
                <h2 className='title-font mb-3 text-lg font-medium text-gray-900'>
                  Neptune
                </h2>
                <p className='text-base leading-relaxed'>
                  Blue bottle crucifix vinyl post-ironic four dollar toast vegan
                  taxidermy. Gastropub indxgo juice poutine, ramps microdosing
                  banh mi pug VHS try-hard.
                </p>
                <a className='mt-3 inline-flex items-center text-indigo-500'>
                  Learn More
                  <svg
                    fill='none'
                    stroke='currentColor'
                    strokeLinecap='round'
                    strokeLinejoin='round'
                    strokeWidth={2}
                    className='ml-2 h-4 w-4'
                    viewBox='0 0 24 24'
                  >
                    <path d='M5 12h14M12 5l7 7-7 7' />
                  </svg>
                </a>
              </div>
            </div>
          </div>
          <button className='mx-auto mt-16 flex rounded border-0 bg-indigo-500 px-8 py-2 text-lg text-white hover:bg-indigo-600 focus:outline-none'>
            Button
          </button>
        </div>
      </section>
    </>
  );
}
